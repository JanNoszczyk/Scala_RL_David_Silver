package easy21.environment

import scala.io.StdIn._

object PlayGame extends App {
  def playerMove(currentState: State.NonTerminalState): Unit = {
    readInt() match {
      case 1 => Environment.step(currentState, Action.Hit) match {
        case (s: State.TerminalState, Reward.Negative) => println("You lost.\n" + s.printState())
        case (s: State.NonTerminalState, _) =>
          println(s.printState() + "\nWhat's your next move?")
          playerMove(s)
      }
      case 0 => Environment.step(currentState, Action.Stick) match {
        case (s, Reward.Positive) => println("You won!\n" + s.printState())
        case (s, Reward.Negative) => println("You lost :(\n" + s.printState())
        case (s, Reward.Zero) => println("It was a draw.\n" + s.printState())
      }
      case _ => println("The input is incorrect. It must be either 1 or 0.")
    }
  }

  val initState = State.NonTerminalState.init()
  println(s"Welcome to easy21.\n${initState.printState()}\nTo hit - enter 1, to stick - enter 0.")
  playerMove(initState)
}
