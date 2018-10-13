import io.swagger.annotations._
import javax.ws.rs._

@Path("/api")
@Api(
  value = "api",
  authorizations = Array(
    new Authorization(value = "JWT"),
    new Authorization(value = "Bearer")
  )
)
@Produces(Array("application/json"))
object Playground {
  @GET @Path("")
  @ApiOperation(
    value = "Get the key with the supplied key ID.",
    response = classOf[Response.User])
  @Produces(Array("application/json"))
  def f(): String = "abcdeefg"
}
