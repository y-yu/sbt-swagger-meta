sys.props.get("plugin.version") match {
  case Some(x) => addSbtPlugin("com.github.y-yu" % "sbt-swagger-meta" % x)
  case _ => sys.error("""|The system property 'plugin.version' is not defined.
                         |Specify this property using the scriptedLaunchOpts -D.""".stripMargin)
}

addSbtPlugin("com.github.battermann" % "sbt-json" % "0.5.0")
