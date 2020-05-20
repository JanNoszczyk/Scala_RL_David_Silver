package easy21.environment

import scala.annotation.tailrec
import scala.util.Random

object Environment extends App {
  def drawCard(): Int = {
    val r = new Random()
    val value = r.nextInt(10) + 1
    if (r.nextInt(3) < 2) value else -value
  }

  def isBust(cardSum: Int): Boolean = cardSum > 21 || cardSum < 1

  def step(state: State.NonTerminalState, action: Action): (State, Reward) = {
    action match {
      case Action.Hit =>
        val newPlayersSum = state.playersSum + drawCard()
        if (isBust(newPlayersSum))
          (State.TerminalState(state.dealersSum, newPlayersSum), Reward.Negative)
        else
          (State.NonTerminalState(state.dealersSum, newPlayersSum), Reward.Zero)
      case Action.Stick =>
        @tailrec
        def dealerHit(dealersSum: Int): Int = {
          if (dealersSum >= 17) dealersSum
          else dealerHit(dealersSum + drawCard())
        }
        val dealersResult = dealerHit(state.dealersSum)
        val terminalState = State.TerminalState(dealersResult, state.playersSum)
        if (isBust(dealersResult))
          (terminalState, Reward.Positive)
        else if (dealersResult < state.playersSum)
          (terminalState, Reward.Positive)
        else if (dealersResult > state.playersSum)
          (terminalState, Reward.Negative)
        else
          (terminalState, Reward.Zero)
    }
  }
}
