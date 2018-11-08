package example.config.di
import com.google.inject.AbstractModule
import example.router.ExampleRouter
class ExampleApiExternalAdapterRouterModule extends AbstractModule {
  def configure(): Unit = {
    bind(classOf[ExampleRouter])
  }

}
