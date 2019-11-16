import sbtswaggermeta.SbtSwaggerMeta.Yaml

enablePlugins(SbtSwaggerMeta)

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.26"

swaggerOutputFileType := Yaml
