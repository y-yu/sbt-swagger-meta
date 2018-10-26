import io.swagger.annotations._
import javax.ws.rs._
import scalaz._

@Path("/api")
@Api(
  value = "api",
  authorizations = Array(
    new Authorization(value = "JWT"),
    new Authorization(value = "Bearer")
  )
)
@Produces(Array("application/json"))
object Main {
  @GET @Path("")
  @ApiOperation(
    value = "Get the key with the supplied key ID.",
    response = classOf[\/[String, Int]])
  @Produces(Array("application/json"))
  def f(): \/[String, Int] = -\/("abcdefg")
}
