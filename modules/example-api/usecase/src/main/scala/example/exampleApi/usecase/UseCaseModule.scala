package example.exampleApi.usecase

import com.google.inject.AbstractModule
import example.exampleApi.usecase.user.create.{CreateUserUseCase, CreateUserUseCaseImpl}
import example.exampleApi.usecase.user.show.{ShowUserUseCase, ShowUserUseCaseImpl}
import example.exampleApi.usecase.uuidsample.{GetUuidUseCase, GetUuidUseCaseImpl}

class UseCaseModule extends AbstractModule {

  def configure(): Unit = {
    bind(classOf[ShowUserUseCase]).to(classOf[ShowUserUseCaseImpl])
    bind(classOf[CreateUserUseCase]).to(classOf[CreateUserUseCaseImpl])
    bind(classOf[GetUuidUseCase]).to(classOf[GetUuidUseCaseImpl])
  }

}
