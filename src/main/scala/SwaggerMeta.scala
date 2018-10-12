import io.swagger.annotations.Api
import io.swagger.jaxrs.Reader
import io.swagger.models.{Info, Swagger}
import io.swagger.util.{Json => UtilJson, Yaml => UtilYaml}
import org.clapper.classutil.ClassFinder
import sbt.internal.PluginManagement.PluginClassLoader
import sbt._
import Keys._
import swagger.meta.Versions
import collection.JavaConverters._

object SwaggerMeta extends AutoPlugin {
  case class SwaggerInfo(
    version: String,
    title: String,
    description: String,
  )

  trait SwaggerOutputFileType {
    val ext: String
  }
  case object Yaml extends SwaggerOutputFileType {
    val ext: String = "yaml"
  }
  case object Json extends SwaggerOutputFileType {
    val ext: String = "json"
  }

  object autoImport {
    lazy val swaggerMeta = TaskKey[File]("swaggerMeta", "generates YAML/JSON files for swagger")

    val swaggerTarget = SettingKey[File]("outputTarget", "target directory that YAML/JSON files would be generated to")

    val swaggerBasePath = SettingKey[String](
      "basePath",
      "The base path on which the API is served, which is relative to the host. If it is not included, the API is served directly under the host. The value MUST start with a leading slash (/). The basePath does not support path templating."
    )

    val swaggerOutputFileType = SettingKey[SwaggerOutputFileType](
      "outputFileType",
      "output file type which is JSON or YAML"
    )

    val swaggerTargetClasspath = SettingKey[String](
      "targetClasspath",
      "target classpath to generate swagger output"
    )

    val swaggerInformation = SettingKey[SwaggerInfo](
      "swaggerInformation",
      "swagger information to initialze"
    )
  }

  import autoImport._

  private val defaultSwaggerInfo = SwaggerInfo(
    version = "2.0",
    title = "API docs",
    description = "",
  )

  override def trigger: PluginTrigger = allRequirements

  override val projectSettings: Seq[Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "io.swagger" % "swagger-core" % Versions.swaggerCoreVersion,
      "io.swagger" % "swagger-jaxrs" % Versions.swaggerCoreVersion,
    ),
    swaggerBasePath := "http://localhost/",
    swaggerOutputFileType := Yaml,
    swaggerTarget := (baseDirectory in Compile).value / "target" / s"swagger.${(swaggerOutputFileType in Compile).value.ext}",
    swaggerTargetClasspath := ".*",
    swaggerInformation := defaultSwaggerInfo,
    swaggerMeta := swaggerMetaTask.dependsOn(compile in Compile).value
  )

  lazy val swaggerMetaTask: Def.Initialize[Task[File]] = Def.task {
    val fullClasspath = (sbt.Keys.fullClasspath in Compile).value
    val classDirectory = (sbt.Keys.classDirectory in Compile).value
    val targetClassPath = (swaggerTargetClasspath in Compile).value
    val targetFile = (swaggerTarget in Compile).value
    val outputFileType = (swaggerOutputFileType in Compile).value
    val swaggerInfo = (swaggerInformation in Compile).value
    val log = sbt.Keys.streams.value.log

    val pluginClassLoader = classOf[Api].getClassLoader.asInstanceOf[PluginClassLoader]

    pluginClassLoader.add(fullClasspath.files.map(_.toURI.toURL))

    log.info(s"== sbt-swagger-meta ==> looking for compiled project classes in: ${classDirectory.toURI.toURL}")

    ClassFinder(Seq(classDirectory)).classpath.foreach {
      f =>
        log.info(s"aaaaaa => ${if (f.isFile) IO.read(f) else "dir"}")
    }

    val projectClassInfos = ClassFinder(Seq(classDirectory)).getClasses().filter {
      classInfo =>
        classInfo.name.matches(targetClassPath)
    }.toList

    pluginClassLoader.add(projectClassInfos.map(_.location.toURI.toURL))

    val projectClasses = projectClassInfos.map {
      classInfo =>
        Class.forName(classInfo.name, false, pluginClassLoader)
    }

    log.info(s"== sbt-swagger-meta ==> loaded ${projectClasses.mkString(",")} project classes")

    val info = new Info()
    info.setTitle(swaggerInfo.title)
    info.setVersion(swaggerInfo.version)
    info.setDescription(swaggerInfo.description)

    val reader = new Reader(new Swagger().info(info))
    val swagger = reader.read(projectClasses.toSet.asJava)

    val body = outputFileType match {
      case `Yaml` => UtilYaml.pretty().writeValueAsString(swagger)
      case `Json` => UtilJson.pretty().writeValueAsString(swagger)
    }

    IO.write(targetFile, body)

    log.info(s"== sbt-swagger-meta ==> generated swagger file to ${targetFile.getPath}")
    targetFile
  }
}
