lazy val root = Project("plugins", file(".")) dependsOn sbtSwaggerMeta

lazy val sbtSwaggerMeta = ClasspathDependency(RootProject(file("..").getAbsoluteFile.toURI), None)
