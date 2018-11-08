package example.exampleApi.domain.model.user
import org.atnos.eff.Eff

import example.shared.lib.dddSupport.domain.{ IdGenerator, Identifier }
import example.shared.lib.eff._
import example.shared.lib.eff.util.idGen.IdGen

case class UUID(value: String) extends Identifier[String]

object UUID {

  def generate[R: _idgen]: Eff[R, UUID] = {
    val gen = new IdGenerator[UUID] {
      override def generate(value: String): UUID = UUID(value)
    }
    IdGen.generate[UUID, R](gen)
  }

}

