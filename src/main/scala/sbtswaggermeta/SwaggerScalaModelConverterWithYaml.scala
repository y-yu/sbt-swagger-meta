package sbtswaggermeta

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import io.swagger.scala.converter.SwaggerScalaModelConverter
import io.swagger.util.{Json, Yaml}
import org.json4s.jackson.Json4sScalaModule

class SwaggerScalaModelConverterWithYaml extends SwaggerScalaModelConverter

object SwaggerScalaModelConverterWithYaml {
  def apply: SwaggerScalaModelConverterWithYaml = {
    Json.mapper().registerModule(Json4sScalaModule)
    Yaml.mapper().registerModules(Json4sScalaModule, DefaultScalaModule)

    new SwaggerScalaModelConverterWithYaml()
  }
}
