package example.shared.lib

import org.atnos.eff._
import org.atnos.eff.addon.monix.TaskEffect

import cats.data.Writer
import example.shared.lib.dddSupport.Error
import example.shared.lib.eff.db.transactionTask.TransactionTaskCreation
import example.shared.lib.eff.util.clock.java8.{ ClockM, ClockMEffect }
import example.shared.lib.eff.util.idGen.{ IdGen, IdGenEffect }
import example.shared.lib.log.LogMessage
import monix.eval.Task

package object eff
  extends ClockMEffect
  with IdGenEffect
  with TransactionTaskCreation
  with example.shared.lib.eff.either.ErrorEffect {

  type _errorEitherMember[R] = ErrorEither <= R
  type _errorEither[R]       = ErrorEither |= R
  type _nothing[R]           = Nothing |= R

  type IdGenStack  = Fx.fx1[IdGen]
  type ClockMStack = Fx.fx1[ClockM]

  type WriterLogMsg[A]  = Writer[LogMessage, A]
  type _writerLogMsg[R] = WriterLogMsg |= R

  type ErrorEither[A]  = Error Either A
  type ModelApplyStack = Fx.fx2[IdGen, ClockM]

  object atnosEff
    extends ReaderEffect
    with WriterEffect
    with StateEffect
    with EvalEffect
    with OptionEffect
    with ListEffect
    with EitherEffect
    with ValidateEffect
    with ChooseEffect
    with SafeEffect
    with MemoEffect
    with Batch
    with EffInterpretation
    with EffCreation
    with EffImplicits
    with TaskEffect

  object atnosEffCreation
    extends ReaderCreation
    with WriterCreation
    with StateCreation
    with EvalCreation
    with OptionCreation
    with ListCreation
    with EitherCreation
    with ValidateCreation
    with ChooseCreation
    with FutureCreation
    with MemoCreation
    with EffCreation
    with SafeCreation

}
