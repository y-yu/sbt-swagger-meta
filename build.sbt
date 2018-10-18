lazy val root = (project in file(".")).
  settings(
    sbtPlugin := true,
    publishMavenStyle := true,
    organization := "com.github.y-yu",
    name := "sbt-swagger-meta",
    // Bintray cannot allow a version which has `-SNAPSHOT` as its postfix,
    // so I will use the postfix of the version is `_SNAPSHOT`.
    version := "0.1.4",
    scalaVersion := "2.12.7",
    crossSbtVersions := Seq("0.13.17", "1.2.4"),
    libraryDependencies ++= Seq(
      "io.swagger" % "swagger-core" % Versions.swagger.core,
      "io.swagger" % "swagger-jaxrs" % Versions.swagger.core,
      "io.swagger" %% "swagger-scala-module" % Versions.swagger.scalaModule excludeAll(
        ExclusionRule("io.swagger", "swagger-core"),
        ExclusionRule("com.fasterxml.jackson.module", "jackson-module-scala")
      ),
      "org.clapper" %% "classutil" % Versions.classutil,
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % Versions.jacksonModuleScala
    ),
    sourceGenerators in Compile += Versions.createVersionsFileTask.taskValue,
    scriptedLaunchOpts := { scriptedLaunchOpts.value ++
      Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
    publishArtifact in Test := false,
    bintrayVcsUrl := Some("git@github.com:y-yu/sbt-swagger-meta.git"),
    bintrayPackage := name.value,
    licenses := Seq("MIT" -> url(s"https://github.com/y-yu/sbt-swagger-meta/LICENCE")),
  ).
  enablePlugins(SbtPlugin)
