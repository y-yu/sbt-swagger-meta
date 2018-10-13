import io.swagger.annotations._
import javax.ws.rs._

@Path("/!!!!!!!!!this-is-changed!!!!!!!!!!!!")
@Produces(Array("application/json"))
object Main {
  @GET @Path("")
  @ApiOperation(
    value = "Get the key with the supplied key ID.",
    response = classOf[String])
  @Produces(Array("application/json"))
  def f(): String = "abcdeefg"
}
