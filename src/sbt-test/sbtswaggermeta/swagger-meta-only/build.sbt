import sbtswaggermeta.SbtSwaggerMeta.Yaml

scalaVersion := "2.12.7"

swaggerOutputFileType := Yaml

TaskKey[Unit]("check") := {
  val body = IO.read(file("target/swagger.yml"))

  if (body.contains("api_c"))
    sys.error("Failed because `api_c` exists in target/swagger.yml!")
}
