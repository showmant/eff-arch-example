package example.shared.lib.transactionTask

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

/**
  * 『PofEAA』の「Unit of Work」パターンの実装
  *
  * トランザクションとはストレージに対するまとまった処理である
  * トランザクションオブジェクトとはトランザクションを表現するオブジェクトで、
  * 具体的にはデータベースライブラリのセッションオブジェクトなどが該当する
  *
  * @tparam Resource トランザクションオブジェクトの型
  * @tparam A トランザクションを実行して得られる値の型
  */
trait TransactionTask[-Resource, +A] { lhs =>

  /**
    * トランザクションの内部で実行される個々の処理の実装
    * このメソッドを実装することでTransactionTaskが作られる
    *
    * @param resource トランザクションオブジェクト
    * @param ec ExecutionContext
    * @return トランザクションの内部で実行される個々の処理で得られる値
    */
  def execute(resource: Resource)(implicit ec: ExecutionContext): Future[A]

  /**
    * TransactionTaskモナドを合成する
    * その際、変位指定によりResourceの型は両方のTransactionTaskのResourceの共通のサブクラスの型になる
    *
    * @param f モナド関数
    * @tparam ExtendedResource トランザクションオブジェクトの型
    * @tparam B 合成されたTransactionTaskを実行すると得られる値の型
    * @return 合成されたTransactionTask
    */
  def flatMap[ExtendedResource <: Resource, B](
    f: A => TransactionTask[ExtendedResource, B]
  ): TransactionTask[ExtendedResource, B] =
    new TransactionTask[ExtendedResource, B] {
      def execute(resource: ExtendedResource)(implicit ec: ExecutionContext): Future[B] =
        lhs.execute(resource).map(f).flatMap(_.execute(resource))
    }

  /**
    * 関数をTransactionTaskの結果に適用する
    *
    * @param f 適用したい関数
    * @tparam B 関数を適用して得られた値の型
    * @return 関数が適用されたTransactionTask
    */
  def map[B](f: A => B): TransactionTask[Resource, B] = flatMap(a => TransactionTask(f(a)))

  /**
    * TransactionTaskRunnerを使ってTransactionTaskを実行する
    * implicitによりResourceに合ったTransactionTaskRunnerが選ばれる
    *
    * @param runner TransactionTaskを実行するためのTransactionTaskRunner
    * @tparam ExtendedResource トランザクションオブジェクトの型
    * @return 個々のTransactionTaskの処理の結果得られる値
    */
  def run[ExtendedResource <: Resource]()(implicit runner: TransactionTaskRunner[ExtendedResource]): Future[A] =
    runner.run(this)
}

object TransactionTask {

  /**
    * TransactionTaskのデータコンストラクタ
    *
    * @param a TransactionTaskの値
    * @tparam Resource トランザクションオブジェクトの型
    * @tparam A TransactionTaskの値の型
    * @return 実行するとaの値を返すTransactionTask
    */
  def apply[Resource, A](a: => A): TransactionTask[Resource, A] =
    new TransactionTask[Resource, A] {
      def execute(resource: Resource)(implicit executor: ExecutionContext): Future[A] =
        Future(a)
    }
}

/**
  * TransactionTaskを実行する
  * トランザクションオブジェクトの型ごとにインスタンスを作成すること
  *
  * @tparam Resource トランザクションオブジェクトの型
  */
trait TransactionTaskRunner[Resource] {

  /**
    * TransactionTaskを実行する
    *
    * @param task 実行するTransactionTask
    * @tparam A TransactionTask実行すると得られる値の型
    * @return TransactionTask実行して得られた値
    */
  def run[A](task: TransactionTask[Resource, A]): Future[A]
}
