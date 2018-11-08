package example.config.di

trait ExampleApiExternalAdapterModule {

  val exampleApiExternalAdapterModules = Seq(
    new ExampleApiExternalAdapterControllerModule(),
    new ExampleApiExternalAdapterRouterModule()
  )


}
