package example.config.di
import com.google.inject.AbstractModule
import example.controller.UserController
import example.shared.lib.eff.util.idGen.interpreter.{IdGenInterpreter, IdGenInterpreterImpl}

class ExampleApiExternalAdapterControllerModule extends AbstractModule {
  def configure(): Unit = {
    bind(classOf[UserController])
    bind(classOf[IdGenInterpreter]).to(classOf[IdGenInterpreterImpl])
  }

}
