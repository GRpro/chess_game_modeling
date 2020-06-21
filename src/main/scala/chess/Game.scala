package chess

import chess.board._
import chess.piece.PieceLocation
import com.whitehatgaming.UserInputFile

object Game {

  def main(args: Array[String]): Unit = {

    val file = args(0)

    //val file = "./data/check_and_counterattack.txt"
    //val file = "./data/check_and_escape.txt"
    //val file = "./data/check_and_capture.txt"

    val input = new UserInputFile(file)

    val it = Iterator.continually(input.nextMove()).takeWhile(_ != null)

    var state = GameState(Board.initial(), White, Set.empty, Set.empty)

    println(state.board)

    def displayCheckFrom(checkFrom: Set[PieceLocation]): String =
      checkFrom.map(pl => s"${pl.piece.displaySymbol}(${pl.location.toStringCanonical})${pl.location.toString}").mkString(",")

    it.map(arr => Move(Location(arr(0), arr(1)), Location(arr(2), arr(3))))
      .foreach { move =>

        val result = Board.move(state, move)

        println(s"${state.currentPlayer}> $move")

        result match {
          case Moved(_, newState, checkFrom, captured) =>
            state = newState
            captured.foreach { piece =>
              println(s"Captured ${piece.displaySymbol}")
            }
            if (checkFrom.nonEmpty) {
              println(s"Check from ${displayCheckFrom(checkFrom)}")
            }
            println(state.board)

          case Invalid(_) =>
            println("INVALID")

          case InvalidLeavesInCheck(_, checkFrom) =>
            println(s"INVALID: leaves in check from ${displayCheckFrom(checkFrom)}")

        }
      }
  }

}
