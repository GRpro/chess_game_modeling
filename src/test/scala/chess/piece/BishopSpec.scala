package chess.piece

import chess.board.{Board, Location}
import chess.{Black, White}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BishopSpec extends AnyWordSpec with Matchers {

  private val board = Board.located(Map(
    Location(0, 0) -> Bishop(Black),
    Location(1, 1) -> Knight(Black),
    Location(4, 2) -> Pawn(Black),
    Location(2, 2) -> Bishop(White)
  ))

  "Bishop" should {

    "move or attack" in {
      Bishop(White).availableAttack(board, Location(2, 2)) shouldBe
        Set(
          // diagonal upper left -> lower right
          Location(1, 1), // takes Knight
          Location(3, 3),
          Location(4, 4),
          Location(5, 5),
          Location(6, 6),
          Location(7, 7),
          // diagonal lower left -> upper right
          Location(0, 4),
          Location(1, 3),
          Location(3, 1),
          Location(4, 0)
        )
    }
  }
}
