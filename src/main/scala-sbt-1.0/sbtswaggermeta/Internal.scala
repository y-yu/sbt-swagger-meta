package sbtswaggermeta

import sbt._
import sbt.internal.PluginManagement.PluginClassLoader
import xsbti.compile.ScalaInstance

private[sbtswaggermeta] object Internal {
  def makeLoader(classpath: Seq[File], parent: ClassLoader, instance: ScalaInstance): ClassLoader
    = internal.inc.classpath.ClasspathUtilities.makeLoader(classpath, parent, instance)

  def makePluginClassLoader(classLoader: ClassLoader): PluginClassLoader = new PluginClassLoader(classLoader)
}
