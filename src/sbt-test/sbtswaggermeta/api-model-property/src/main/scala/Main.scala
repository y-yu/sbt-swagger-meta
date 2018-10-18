import io.swagger.annotations._
import javax.ws.rs._
import scala.annotation.meta.field

@Path("/api")
@Api(value = "api")
@Produces(Array("application/json"))
object Main {
  @GET @Path("")
  @ApiOperation(
    value = "Get the user",
    response = classOf[User])
  def f(): User = ???
}

case class User(
  @(ApiModelProperty @field)(value = "user's email address") email: Email,
  @(ApiModelProperty @field)(value = "user name") name: String
)

case class Email(
  @(ApiModelProperty @field)(value = "email address") value: String
)
