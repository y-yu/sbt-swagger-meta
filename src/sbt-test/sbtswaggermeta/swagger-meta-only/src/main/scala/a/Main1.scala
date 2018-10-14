package a

import io.swagger.annotations._
import javax.ws.rs._

@Path("/api_a")
@Api(
  value = "api_a",
)
@Produces(Array("application/json"))
object Main1 {
  @GET @Path("")
  @Produces(Array("application/json"))
  def f(): String = "abcdeefg"
}
