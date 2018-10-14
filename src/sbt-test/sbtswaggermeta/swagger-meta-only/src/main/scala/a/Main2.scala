package a

import io.swagger.annotations._
import javax.ws.rs._

@Path("/api_b")
@Api(
  value = "api_b",
)
@Produces(Array("application/json"))
object Main2 {
  @GET @Path("")
  @Produces(Array("application/json"))
  def f(): String = "abcdeefg"
}
