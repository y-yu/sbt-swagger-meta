import sbtswaggermeta.SbtSwaggerMeta.Yaml

scalaVersion := "2.12.7"

enablePlugins(SbtSwaggerMeta)

swaggerOutputFileType := Yaml
