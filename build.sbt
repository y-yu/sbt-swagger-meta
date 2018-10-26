import ReleaseTransformations._
import UpdateReadme.updateReadme

lazy val root = (project in file(".")).
  settings(
    sbtPlugin := true,
    organization := "com.github.y-yu",
    name := "sbt-swagger-meta",
    description := "A sbt plugin for generating a Swagger documentation",
    homepage := Some(url("https://github.com/y-yu")),
    licenses := Seq("MIT" -> url(s"https://github.com/y-yu/sbt-swagger-meta/blob/master/LICENSE")),
    scalaVersion := "2.12.7",
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-Xlint",
      "-language:implicitConversions", "-language:higherKinds", "-language:existentials",
      "-unchecked"
    ),
    crossSbtVersions := Seq("0.13.17", "1.2.6"),
    libraryDependencies ++= Seq(
      "io.swagger" % "swagger-core" % Versions.swagger.core,
      "io.swagger" % "swagger-jaxrs" % Versions.swagger.core,
      "io.swagger" %% "swagger-scala-module" % Versions.swagger.scalaModule excludeAll(
        ExclusionRule("io.swagger", "swagger-core"),
        ExclusionRule("com.fasterxml.jackson.module", "jackson-module-scala")
      ),
      "org.clapper" %% "classutil" % Versions.classutil,
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % Versions.jacksonModuleScala,
      "org.json4s" %% "json4s-jackson" % Versions.json4s
    ),
    sourceGenerators in Compile += Versions.createVersionsFileTask.taskValue,
    scriptedLaunchOpts := { scriptedLaunchOpts.value ++
      Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false
  ).
  settings(publishSettings).
  enablePlugins(SbtPlugin)

lazy val publishSettings = Seq(
  publishMavenStyle := true,
  publishTo := Some(
    if (isSnapshot.value)
      Opts.resolver.sonatypeSnapshots
    else
      Opts.resolver.sonatypeStaging
  ),
  publishArtifact in Test := false,
  pomExtra :=
    <developers>
      <developer>
        <id>y-yu</id>
        <name>Hikaru Yoshimura</name>
        <url>https://github.com/y-yu</url>
      </developer>
    </developers> 
    <scm>
      <url>git@github.com:y-yu/sbt-swagger-meta.git</url>
      <connection>scm:git:git@github.com:y-yu/sbt-swagger-meta.git</connection>
      <tag>{tagOrHash.value}</tag>
    </scm>,
  releaseTagName := tagName.value,
  releaseCrossBuild := true,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    releaseStepCommandAndRemaining("^ scripted"),
    setReleaseVersion,
    updateReadme,
    commitReleaseVersion,
    tagRelease,
    releaseStepCommandAndRemaining("^ publishSigned"),
    setNextVersion,
    updateReadme,
    commitNextVersion,
    releaseStepCommand("sonatypeReleaseAll"),
    pushChanges
  )
)

val tagName = Def.setting {
  s"v${if (releaseUseGlobalVersion.value) (version in ThisBuild).value else version.value}"
}

val tagOrHash = Def.setting {
  if (isSnapshot.value) sys.process.Process("git rev-parse HEAD").lineStream_!.head
  else tagName.value
}
