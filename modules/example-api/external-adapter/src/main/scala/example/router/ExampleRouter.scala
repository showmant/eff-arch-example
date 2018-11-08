package example.router

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.google.inject.Inject
import example.akkaHttp.AbstractAkkaHttpRouter
import example.controller.UserController

class ExampleRouter @Inject()(userController: UserController) extends AbstractAkkaHttpRouter {

  override def routes: Route =
    path("user") {
      get {
        userController.show()
      }

    }
}
