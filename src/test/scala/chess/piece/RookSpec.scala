package chess.piece

import chess.board.{Board, Location}
import chess.{Black, White}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class RookSpec extends AnyWordSpec with Matchers {

  private val board = Board.located(Map(
    Location(0, 0) -> Bishop(Black),
    Location(1, 1) -> Knight(Black),
    Location(4, 2) -> Pawn(Black),
    Location(2, 2) -> Rook(White)
  ))

  "Rook" should {

    "move or attach" in {
      Rook(White).availableAttack(board, Location(2, 2)) shouldBe
        Set(
          // horizontal
          Location(0, 2),
          Location(1, 2),
          Location(3, 2),
          Location(4, 2), // takes Pawn
          // vertical
          Location(2, 0),
          Location(2, 1),
          Location(2, 3),
          Location(2, 4),
          Location(2, 5),
          Location(2, 6),
          Location(2, 7)
        )
    }
  }
}

