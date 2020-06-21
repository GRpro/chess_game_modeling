package chess.piece

import chess.board.{Board, Location}
import chess.{Black, White}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class KnightSpec extends AnyWordSpec with Matchers {

  private val board = Board.located(
    Map(
      Location(3, 3) -> Knight(White),
      Location(1, 2) -> Rook(Black),
      Location(2, 1) -> Rook(Black),
      Location(5, 4) -> Pawn(Black),
      Location(4, 5) -> Pawn(Black),
      Location(1, 4) -> Knight(Black),
      Location(2, 5) -> Knight(Black),
      Location(5, 2) -> Knight(White), // White !
      Location(4, 1) -> Pawn(Black)
    )
  )

  // println(board)

  "Knight" should {

    "move or attack" in {

      Knight(White).availableAttackOrMove(board, Location(3, 3)) shouldBe
        Set(
          Location(1, 2),
          Location(2, 1),
          Location(5, 4),
          Location(4, 5),
          Location(1, 4),
          Location(2, 5),
          Location(4, 1)
        )
    }
  }
}
