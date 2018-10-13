package b

import io.swagger.annotations._
import javax.ws.rs._

@Path("/api_c")
@Api(
  value = "api_c",
)
@Produces(Array("application/json"))
object Main3 {
  @GET @Path("")
  @Produces(Array("application/json"))
  def f(): String = "abcdeefg"
}
