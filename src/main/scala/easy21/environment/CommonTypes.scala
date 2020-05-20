package easy21.environment

import scala.util.Random


sealed trait State {
  def dealersSum: Int
  def playersSum: Int
  def printState(): String
}
object State {
  final case class NonTerminalState(
     dealersSum: Int,
     playersSum: Int
 ) extends State {
    require(
      1 <= dealersSum && dealersSum <= 10 && 1 <= playersSum && playersSum <= 21,
      "The input State parameters are out of bounds."
    )

    override def printState(): String =
      s"The dealer's first card was ${this.dealersSum}. The player's current sum is ${this.playersSum}."
  }

  object NonTerminalState {
    def init(): NonTerminalState = {
      val r = new Random()
      def drawBlackCard() = r.nextInt(10) + 1
      NonTerminalState(drawBlackCard(), drawBlackCard())
    }
  }

  final case class TerminalState(
     dealersSum: Int,
     playersSum: Int
 ) extends State {
    override def printState(): String =
      s"The dealer's sum was ${this.dealersSum}. The player's sum was ${this.playersSum}."
  }
}


sealed trait Action
object Action {
  case object Hit extends Action
  case object Stick extends Action
}


sealed trait Reward
object Reward {
  case object Positive extends Reward
  case object Negative extends Reward
  case object Zero extends Reward
}

