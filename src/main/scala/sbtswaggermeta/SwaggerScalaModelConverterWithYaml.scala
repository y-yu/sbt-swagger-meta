package sbtswaggermeta

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import io.swagger.scala.converter.SwaggerScalaModelConverter
import io.swagger.util.Yaml

class SwaggerScalaModelConverterWithYaml extends SwaggerScalaModelConverter

object SwaggerScalaModelConverterWithYaml {
  def apply: SwaggerScalaModelConverterWithYaml = {
    Yaml.mapper().registerModule(DefaultScalaModule)
    new SwaggerScalaModelConverterWithYaml()
  }
}
