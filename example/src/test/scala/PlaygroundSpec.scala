package playground

import org.scalatest._
import meta._

class PlaygroundSpec extends FunSuite {
  test("macro test") {
    val path = "playground/src/main/scala/Playground.scala"
    val c = new meta.SwaggerGen()
    c.execute(path)
  }
}
