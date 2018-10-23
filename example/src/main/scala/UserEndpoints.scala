import io.swagger.annotations._
import javax.ws.rs._
import scala.annotation.meta.field

@Path("/users") @Api(value = "/users")
@Produces(Array("application/json"))
object UserEndpoints {
  @GET @Path("")
  @ApiOperation(value = "Get the key with the supplied key ID.")
  @Produces(Array("application/json"))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message =
      "Success. Body contains key and creator information.",
      response = classOf[Response.User]
    ),
    new ApiResponse(code = 400, message =
      "Bad Request. Errors specify: (snip)",
      response = classOf[Response.BadRequest]),
    new ApiResponse(code = 404, message = "Not Found.",
      response = classOf[Response.NotFound])))
  def getByEmail(email: String): Response.User = ???
}

sealed trait Response
object Response {
  case class User(
    @(ApiModelProperty @field)(value = "email address") email: String
  ) extends Response

  case class BadRequest(
    @(ApiModelProperty @field)(value = "error message") msg: String
  ) extends Response

  case class NotFound(
    @(ApiModelProperty @field)(value = "error messages") msg: Seq[String]
  ) extends Response
}
