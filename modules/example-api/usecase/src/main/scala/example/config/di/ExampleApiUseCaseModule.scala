package example.config.di
import example.exampleApi.usecase.UseCaseModule

trait ExampleApiUseCaseModule {
  val exampleApiUseCaseModules = Seq(
    new UseCaseModule()
  )

}
