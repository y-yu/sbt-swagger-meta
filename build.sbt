lazy val root = (project in file(".")).
  settings(
    sbtPlugin := true,
    publishMavenStyle := true,
    organization := "com.github.y-yu",
    name := "sbt-swagger-meta",
    // bintrayが`-SNAPSHOT`というバージョンを許してくれないので、やむを得ず`_SNAPSHOT`としている
    version := "0.1.0_SNAPSHOT",
    scalaVersion := "2.12.7",
    libraryDependencies ++= Seq(
      "io.swagger" % "swagger-core" % Versions.swagger.core,
      "io.swagger" % "swagger-jaxrs" % Versions.swagger.core,
      "org.clapper" %% "classutil" % Versions.classutil
    ),
    sourceGenerators in Compile += Versions.createVersionsFileTask.taskValue,
    scriptedLaunchOpts := { scriptedLaunchOpts.value ++
      Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
    publishArtifact in Test := false,
    bintrayVcsUrl := Some("git@github.com:y-yu/sbt-swagger-meta.git"),
    bintrayPackage := name.value,
    licenses := Seq("MIT" -> url(s"https://opensource.org/licenses/MIT")),
  ).
  enablePlugins(SbtPlugin)
