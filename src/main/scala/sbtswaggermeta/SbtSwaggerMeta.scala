package sbtswaggermeta

import io.swagger.annotations.Api
import io.swagger.jaxrs.Reader
import io.swagger.converter.ModelConverters
import io.swagger.models.{Info, Swagger}
import io.swagger.util.{Json => UtilJson, Yaml => UtilYaml}
import sbt.complete.Parser
import sbt.complete.DefaultParsers._
import org.clapper.classutil.ClassFinder
import sbt.Keys._
import sbt._
import scala.collection.JavaConverters._

object SbtSwaggerMeta extends AutoPlugin {
  case class SwaggerInfo(
    version: String,
    title: String,
    description: String
  )

  trait SwaggerOutputFileType {
    val ext: String
  }
  case object Yaml extends SwaggerOutputFileType {
    val ext: String = "yml"
  }
  case object Json extends SwaggerOutputFileType {
    val ext: String = "json"
  }

  object autoImport {
    lazy val swaggerMeta = TaskKey[Unit]("swagger-meta", "Generates Swagger YAML/JSON files.")

    lazy val swaggerMetaOnly = InputKey[Unit]("swagger-meta-only", "Generates Swagger YAML/JSON files for files which have the specified classpath.")

    lazy val swaggerTarget = SettingKey[File]("output-target", "Target file that YAML/JSON files would be generated to.")

    lazy val swaggerBasePath = SettingKey[String]("base-path", "Base path where the API is served.")

    lazy val swaggerOutputFileType = SettingKey[SwaggerOutputFileType]("output-file-type", "Output file type which is JSON or YAML")

    lazy val swaggerTargetClasspath = SettingKey[String]("target-classpath", "Target classpath to generate swagger output as a Regex.")

    lazy val swaggerInformation = SettingKey[SwaggerInfo]("swagger-information", "Swagger information to initialize")
  }

  import autoImport._

  private val defaultSwaggerInfo = SwaggerInfo(
    version = "2.0",
    title = "API docs",
    description = ""
  )

  override def trigger: PluginTrigger = noTrigger

  override val projectSettings: Seq[Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "io.swagger" % "swagger-core" % Versions.swaggerCoreVersion,
      "io.swagger" % "swagger-jaxrs" % Versions.swaggerCoreVersion
    ),
    swaggerBasePath := "http://localhost/",
    swaggerOutputFileType := Yaml,
    swaggerTarget :=
      (Compile / baseDirectory).value / "target" / s"swagger.${(Compile / swaggerOutputFileType).value.ext}",
    swaggerTargetClasspath := ".*",
    swaggerInformation := defaultSwaggerInfo,
    swaggerMeta := swaggerMetaTask(None).dependsOn(Compile / compile).value,
    swaggerMetaOnly := Def.inputTaskDyn {
      val targetClasspath = argsParser.parsed

      swaggerMetaTask(Some(targetClasspath)).dependsOn(Compile / compile)
    }.evaluated
  )

  lazy val argsParser: Parser[String] =
    ((Space ~> StringBasic).+ <~ SpaceClass.*) map { args =>
      s"(${args.mkString("|")})"
    }

  def swaggerMetaTask(targetClasspathOpt: Option[String]): Def.Initialize[Task[Unit]] = Def.task {
    val fullClasspath = (Compile / sbt.Keys.fullClasspath).value
    val classDirectory = (Compile / sbt.Keys.classDirectory).value
    val targetFile = (Compile / swaggerTarget).value
    val outputFileType = (Compile / swaggerOutputFileType).value
    val swaggerInfo = (Compile / swaggerInformation).value
    val scalaInstanceInCompile = (Compile / scalaInstance).value
    val log = sbt.Keys.streams.value.log
    val targetClasspath = targetClasspathOpt match {
      case Some(t) => t
      case None => (Compile / swaggerTargetClasspath).value
    }

    val mainClassLoader = Internal.makeLoader(fullClasspath.map(_.data), classOf[Api].getClassLoader, scalaInstanceInCompile)
    val pluginClassLoader = Internal.makePluginClassLoader(mainClassLoader)

    pluginClassLoader.add(fullClasspath.files.map(_.toURI.toURL))

    log.info(s"== sbt-swagger-meta ==> Looking for compiled classes in: ${classDirectory.toURI.toURL}")

    val projectClassInfo = ClassFinder(Seq(classDirectory)).getClasses().filter {
      classInfo =>
        classInfo.name.matches(targetClasspath)
    }.toList

    pluginClassLoader.add(projectClassInfo.map(_.location.toURI.toURL))

    val projectClasses = projectClassInfo.map {
      classInfo =>
        Class.forName(classInfo.name, false, pluginClassLoader)
    }

    log.info(s"== sbt-swagger-meta ==> Loaded classes: ${projectClasses.mkString(",")}")

    val info = new Info()
    info.setTitle(swaggerInfo.title)
    info.setVersion(swaggerInfo.version)
    info.setDescription(swaggerInfo.description)

    val reader = new Reader(new Swagger().info(info))

    ModelConverters.getInstance().addConverter(new SwaggerScalaModelConverterWithYaml)

    val swagger = reader.read(projectClasses.toSet.asJava)

    val body = outputFileType match {
      case `Yaml` => UtilYaml.pretty().writeValueAsString(swagger)
      case `Json` => UtilJson.pretty().writeValueAsString(swagger)
    }

    IO.write(targetFile, body)

    log.info(s"== sbt-swagger-meta ==> Generated a swagger file: ${targetFile.getPath}")
  }
}
