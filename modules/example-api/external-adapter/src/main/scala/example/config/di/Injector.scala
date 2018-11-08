package example.config.di
import com.google.inject.{ Guice, Module }

import example.exampleApi.secondaryAdapter.config.di.ExampleSecondaryAdapterModules
import example.shared.adapter.config.di.SharedSecondaryAdapterModules

trait Injector
  extends ExampleSecondaryAdapterModules
  with ExampleApiExternalAdapterModule
  with ExampleApiUseCaseModule
  with SharedExternalAdapterModules
  with SharedSecondaryAdapterModules {

  val modules: Seq[Module] =
  exampleSecondaryAdapterModules ++
//  exampleApiExternalAdapterModules ++
  exampleApiUseCaseModules ++
  sharedExternalAdapterModules ++
  sharedSecondaryAdapterModules

  val injector: com.google.inject.Injector = Guice.createInjector(modules: _*)

}
