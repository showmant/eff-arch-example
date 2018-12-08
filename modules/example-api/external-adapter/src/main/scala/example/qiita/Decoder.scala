package example.qiita

import cats.data.ValidatedNel
import cats.implicits._
import io.circe.{ AccumulatingDecoder, Decoder, DecodingFailure, HCursor }
import io.tabmo.circe.extra.rules.{ IntRules, StringRules }
import io.tabmo.json.rules._

case class Person(firstName: String, lastName: String, age: Int, email: String)

object AlpDecoder {
  //これでいけるやん！
  //2つの課題
  //1. readのレスポンスが Either[DecodingFailure, A] であること。故にこのままだとエラーを積めないので不親切なフォームバリデーションになってしまう
  //2. 試してみるとやはり同一のプロパティに対して複数のバリデーションを実装することはできるんだけど、1つ目の評価でエラーになると、次が評価されない
  val decodePerson: Decoder[Person] = Decoder.instance[Person] { c: HCursor =>
    for {
      firstName <- c.downField("firstName").read(StringRules.maxLength(32))
      lastName  <- c.downField("lastName").as[String]
      age       <- c.downField("age").read(IntRules.positive())
      email     <- c.downField("email").read(StringRules.email)
    } yield Person(firstName, lastName, age, email)
  }

  //circeに内蔵されているAccumulatingDecoderクラスをDecoderクラスの代わりに使えば、課題1は解消されそうじゃない？？
  // Stringをうけとってparseをするときに与えなければならない型が必要なのがDecoderクラスであってAccumulatingDecoderじゃない。。。ダメだ。。。
  // どうやらこのクラスはDecoderの内部で使われているクラス?
  implicit def toValidatedNel[A](either: Either[DecodingFailure, A]): ValidatedNel[DecodingFailure, A] = ???
  implicit def decoder2: AccumulatingDecoder[Person] = AccumulatingDecoder.instance[Person] { c: HCursor =>
    for {
      firstName <- c.downField("firstName").read(StringRules.maxLength(32))
      lastName  <- c.downField("lastName").as[String]
      age       <- c.downField("age").read(IntRules.positive())
      email     <- c.downField("email").read(StringRules.email)
    } yield Person(firstName, lastName, age, email)
  }

  def decoder3: Decoder[Person] =
    (firstNameDecoder, lastNameDecoder, ageDecoder, emailDecoder).mapN(Person.apply)

  private[this] def firstNameDecoder: Decoder[String] =
    Decoder.instance(_.downField("user_id").read(StringRules.isNotEmpty()))
  private[this] def lastNameDecoder: Decoder[String] =
    Decoder.instance(_.downField("lastName").read(StringRules.maxLength(32)))
  private[this] def ageDecoder: Decoder[Int]      = Decoder.instance(_.downField("age").read(IntRules.positive()))
  private[this] def emailDecoder: Decoder[String] = Decoder.instance(_.downField("age").read(StringRules.email))

}

case class SomeError(s: String) extends Throwable

//結果としてたどり着いた手法がこちら
//class ShowUserRequestDecoder() {
//
//  //TODO : Rulesはライブラリをラップしてメッセージとかちゃんとするようにする
//  def create: Decoder[ShowUserRequest] =
//    (userIdDecoder, nameDecoder, ageDecoder).mapN(ShowUserRequest.apply)
//
//  private[this] def userIdDecoder: Decoder[String] =
//    Decoder.instance(_.downField("user_id").read(StringRules.isNotEmpty()))
//  private[this] def nameDecoder: Decoder[Name] = Decoder.instance(_.get[Name]("name"))
//  private[this] def ageDecoder: Decoder[Int]   = Decoder.instance(_.downField("age").read(IntRules.positive()))
//
//  private[this] implicit def name: Decoder[Name] = (firstNameDecoder, lastNameDecoder).mapN(Name.apply)
//
//  private[this] def firstNameDecoder: Decoder[String] =
//    Decoder.instance(_.downField("first_name").read(StringRules.isNotEmpty()))
//  private[this] def lastNameDecoder: Decoder[String] =
//    Decoder.instance(_.downField("last_name").read(StringRules.notBlank()))
//}
