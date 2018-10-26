import sbtswaggermeta.SbtSwaggerMeta.Yaml

scalaVersion := "2.12.7"

enablePlugins(SbtSwaggerMeta)

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.26"

swaggerOutputFileType := Yaml
