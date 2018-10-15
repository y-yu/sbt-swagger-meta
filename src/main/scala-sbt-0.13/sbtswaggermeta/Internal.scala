package sbtswaggermeta

import sbt._
import PluginManagement._

private[sbtswaggermeta] object Internal {
  def makeLoader(classpath: Seq[File], parent: ClassLoader, instance: ScalaInstance): ClassLoader
    = sbt.classpath.ClasspathUtilities.makeLoader(classpath, parent, instance)

  def makePluginClassLoader(classLoader: ClassLoader): PluginClassLoader = new PluginClassLoader(classLoader)
}
