import sbtswaggermeta.SbtSwaggerMeta.Yaml

swaggerOutputFileType := Yaml

enablePlugins(SbtSwaggerMeta)

TaskKey[Unit]("check") := {
  val body = IO.read(file("target/swagger.yml"))

  if (body.contains("api_c"))
    sys.error("Failed because `api_c` exists in target/swagger.yml!")
}
