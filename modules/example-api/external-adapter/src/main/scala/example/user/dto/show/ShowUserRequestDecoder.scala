package example.user.dto.show
import cats.data.{ NonEmptyList, Validated, Writer }
import cats.data.Validated.{ Invalid, Valid }
import cats.implicits._
import io.circe.Decoder.Result
import io.circe.{ AccumulatingDecoder, Decoder, DecodingFailure, HCursor }
import io.tabmo.circe.extra.rules.{ IntRules, StringRules }
import io.tabmo.json.rules._

class ShowUserRequestDecoder() {

  //TODO : Rulesはライブラリをラップしてメッセージとかちゃんとするようにする
  def create: Decoder[ShowUserRequest] =
    (userIdDecoder, nameDecoder, ageDecoder).mapN(ShowUserRequest.apply)

  private[this] def userIdDecoder: Decoder[String] =
    Decoder.instance(_.downField("user_id").read(StringRules.isNotEmpty()))
  private[this] def nameDecoder: Decoder[Name] = Decoder.instance(_.get[Name]("name"))
  private[this] def ageDecoder: Decoder[Int]   = Decoder.instance(_.downField("age").read(IntRules.positive()))

  private[this] implicit def name: Decoder[Name] = (firstNameDecoder, lastNameDecoder).mapN(Name.apply)

  private[this] def firstNameDecoder: Decoder[String] =
    Decoder.instance(_.downField("first_name").read(StringRules.isNotEmpty()))
  private[this] def lastNameDecoder: Decoder[String] =
    Decoder.instance(_.downField("last_name").read(StringRules.notBlank()))

  implicit def create2: AccumulatingDecoder[ShowUserRequest] = AccumulatingDecoder.instance[ShowUserRequest] {
    c: HCursor =>
//      val name = c.downField("name").read(StringRules.maxLength(32)).toValidatedNel
//      val lastName = c.downField("lastName").as[String].toValidatedNel
//      val age = c.downField("age").read(IntRules.positive()).toValidatedNel
//      val email = c.downField("email").read(StringRules.email).toValidatedNel
      val name: Result[String]     = c.downField("name").read(StringRules.maxLength(32))
      val lastName: Result[String] = c.downField("lastName").as[String]
      val age: Result[Int]         = c.downField("age").read(IntRules.positive())
      val email: Result[String]    = c.downField("email").read(StringRules.email)
      val list                     = Seq(name, lastName, age, email)
      val a: Result[ShowUserRequest] = (name, lastName, age, email).mapN {
        case (n, l, ag, e) => ShowUserRequest(n, Name(n, l), ag)
      }
      ???

  }

//  implicit def create3: AccumulatingDecoder[ShowUserRequest] = AccumulatingDecoder.instance[ShowUserRequest] {
//    c: HCursor =>
//      //      val name = c.downField("name").read(StringRules.maxLength(32)).toValidatedNel
//      //      val lastName = c.downField("lastName").as[String].toValidatedNel
//      //      val age = c.downField("age").read(IntRules.positive()).toValidatedNel
//      //      val email = c.downField("email").read(StringRules.email).toValidatedNel
//      val name: Validated[DecodingFailure, String] = c.downField("name").read(StringRules.maxLength(32)).toValidated
//      val lastName = c.downField("lastName").as[String].toValidated
//      val age = c.downField("age").read(IntRules.positive()).toValidated
//      val email = c.downField("email").read(StringRules.email).toValidated
//      for {
//      n <- name
//      l <- lastName
//      ag <- age
//      e <-  email
//      } yield ???
//      val a = (name, lastName, age, email).mapN { case (n, l, ag ,e) => ShowUserRequest(n, Name(n,l), ag)}
//      ???
//
//  }

  implicit def create4: AccumulatingDecoder[ShowUserRequest] = AccumulatingDecoder.instance[ShowUserRequest] {
    c: HCursor =>
      //      val name = c.downField("name").read(StringRules.maxLength(32)).toValidatedNel
      //      val lastName = c.downField("lastName").as[String].toValidatedNel
      //      val age = c.downField("age").read(IntRules.positive()).toValidatedNel
      //      val email = c.downField("email").read(StringRules.email).toValidatedNel
      val name: Writer[List[DecodingFailure], String] = c.downField("name").read(StringRules.maxLength(32)) match {
        case Right(s) => Writer.value(s)
        case Left(e)  => Writer(List(e), "")
      }
      val lastName: Writer[List[DecodingFailure], String] = c.downField("lastName").as[String] match {
        case Right(s) => Writer.value(s)
        case Left(e)  => Writer(List(e), "")
      }
      val age: Writer[List[DecodingFailure], Int] = c.downField("age").read(IntRules.positive()) match {
        case Right(s) => Writer.value(s)
        case Left(e)  => Writer(List(e), 0)
      }
      val email: Writer[List[DecodingFailure], String] = c.downField("email").read(StringRules.email) match {
        case Right(s) => Writer.value(s)
        case Left(e)  => Writer(List(e), "")
      }

      (for {
        n  <- name
        l  <- lastName
        ag <- age
      } yield ShowUserRequest(n, Name(n, l), ag)).run match {
        case (Nil, b)    => Valid(b)
        case (h :: t, _) => Invalid(NonEmptyList(h, t))
      }

  }
}
