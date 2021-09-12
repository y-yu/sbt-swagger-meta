import sbt._
import sbt.Keys._

object Versions {
  val swagger = new {
    val core = "1.6.2"
    val scalaModule = "1.0.6"
  }
  val classUtil = "1.5.1"

  val jacksonModuleScala = "2.12.5"

  val jaxbApi = "2.3.1"

  val json4s = "3.6.1"

  lazy val createVersionsFileTask: Def.Initialize[Task[Seq[File]]] = Def.task {
    val output = (Compile / sourceManaged).value / "sbtswaggermeta" / "Versions.scala"

    val versionsBody =
      s"""/**
         |  * This file was generated by `project/Versions.scala` in sbt-swagger-meta.
         |  * So you must not edit this file.
         |  */
         |
         |package sbtswaggermeta
         |
         |object Versions {
         |  val swaggerCoreVersion: String = "${swagger.core}"
         |}
         |""".stripMargin

    IO.write(output, versionsBody)
    Seq(output)
  }
}
