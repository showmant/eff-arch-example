package example
import example.akkaHttp.Request
import example.circe.CirceDecoder
import example.user.dto.show.{ ShowUserRequest, ShowUserRequestDecoder }
import io.circe.{ AccumulatingDecoder, Decoder }

sealed trait RequestDecoder[A <: Request] extends CirceDecoder[A]

object RequestDecoder {
  implicit object ShowUserRequestDecoder extends RequestDecoder[ShowUserRequest] {
    protected override implicit def decoder: AccumulatingDecoder[ShowUserRequest] =
      new ShowUserRequestDecoder().create4
  }

}
