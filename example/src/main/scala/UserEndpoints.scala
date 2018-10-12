import io.swagger.annotations._
import javax.ws.rs._

@Path("/users") @Api(value = "/users")
@Produces(Array("application/json"))
object UserEndpoints {
  // Actual controller part for your framework
  @GET @Path("")
  @ApiOperation(
    value = "Get the key with the supplied key ID.",
    response = classOf[Response.User])
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
  def getByEmail(email: String): Option[String] =
  // Implement something meaningfull here.
    ???
}

// These are supposed to be defined somewhere else,
// and to be able to transform to JSON
sealed trait Response
object Response {
  case class User(email: String) extends Response
  case class BadRequst(msg: String) extends Response
  case class NotFound(msg: String) extends Response
}
