package example.circe

import cats.data.Validated.{ Invalid, Valid }
import cats.data.{ NonEmptyList, Validated, ValidatedNel }
import example.shared.lib.dddSupport.Error
import io.circe
import io.circe.jawn.JawnParser
import io.circe.{ AccumulatingDecoder, Decoder, Json, Parser, ParsingFailure }
//import io.circe.parser._

trait NewParser extends Parser {
  protected[this] final def finishDecodeAccumulating[A](
    input: Either[ParsingFailure, Json]
  )(implicit decoder: AccumulatingDecoder[A]): ValidatedNel[io.circe.Error, A] = input match {
    case Right(json) =>
      decoder(json.hcursor).leftMap {
        case NonEmptyList(h, t) => NonEmptyList(h, t)
      }
    case Left(error) => Validated.invalidNel(error)
  }

  final def decodeAccumulating[A: AccumulatingDecoder](input: String): ValidatedNel[io.circe.Error, A] =
    finishDecodeAccumulating(parse(input))

  private[this] val parser                               = new JawnParser
  def parse(input: String): Either[ParsingFailure, Json] = parser.parse(input)
}

abstract class CirceDecoder[A] extends CirceErrorConverter with NewParser {

  protected implicit def decoder: AccumulatingDecoder[A]

  def decode(jsonStr: String): Either[Error, A] =
    decodeAccumulating(jsonStr) match {
      case Valid(value)    => Right(value)
      case Invalid(errors) => Left(convert(errors.toList))

    }
  def decode2(jsonStr: String): ValidatedNel[circe.Error, A] = decodeAccumulating(jsonStr)
}
