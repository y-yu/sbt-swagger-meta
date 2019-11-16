import sbtswaggermeta.SbtSwaggerMeta.{Json => JsonType}

enablePlugins(SbtSwaggerMeta)

swaggerOutputFileType := JsonType

TaskKey[Unit]("check") := {
  import play.api.libs.json._
  import play.api.libs.json.Reads._
  import play.api.libs.functional.syntax._

  val body = Json.parse(IO.read(file("target/swagger.json")))
  val expected = Json.parse(
    """
      |{
      |  "Email": {
      |    "type": "object",
      |    "properties": {
      |      "value": {
      |        "type": "string",
      |        "description": "email address"
      |      }
      |    }
      |  },
      |  "User": {
      |    "type": "object",
      |    "properties": {
      |      "email": {
      |        "description": "user's email address",
      |        "$ref": "#/definitions/Email"
      |      },
      |      "name": {
      |        "type": "string",
      |        "description": "user name"
      |      }
      |    }
      |  }
      |}
    """
    .stripMargin)

  val actual = (body \ "definitions").get

  if (actual != expected) {
    println(s"Actual: ${Json.prettyPrint(actual)}")
    println(s"Expected: ${Json.prettyPrint(expected)}")
    sys.error("Failed because of failing to get properties of `User` case class")
  }
}
