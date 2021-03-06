package example.exampleApi.domain.repository.user
import org.atnos.eff.Eff

import example.exampleApi.domain.model.user.{ User, UserId }
import example.shared.lib.dddSupport.adapter.secondary.repository.Repository
import example.shared.lib.eff._trantask

abstract class UserRepository extends Repository[UserId, User] {
  def resolveById[R: _trantask](id: UserId): Eff[R, Option[User]]
  def store[R: _trantask](entity: User): Eff[R, User]
  def remove[R: _trantask](id: UserId): Eff[R, Unit]
}
