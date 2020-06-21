package chess.piece

import chess.board.{Board, Location}
import chess.{Black, White}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PieceSpec extends AnyWordSpec with Matchers {

  private val queenLocation = Location(4, 4)
  private val board = Board.located(
    Map(
      Location(1, 1) -> Pawn(White),
      Location(7, 7) -> Pawn(Black),
      Location(5, 4) -> Bishop(Black),
      queenLocation -> Queen(Black)
    )
  )


  "tillNotAttacked" should {

    "take opponent piece and stop" in {
      Queen(Black).tillNotAttacked(board, queenLocation.x, queenLocation.y, _ - 1, _ - 1) shouldBe
        Set(
          Location(3, 3),
          Location(2, 2),
          Location(1, 1) // takes Pawn
        )
    }

    "not take own piece" in {
      Queen(Black).tillNotAttacked(board, queenLocation.x, queenLocation.y, _ + 1, _ + 1) shouldBe
        Set(
          Location(5, 5),
          Location(6, 6)
        )
    }

    "stop on border" in {
      Queen(Black).tillNotAttacked(board, queenLocation.x, queenLocation.y, _ - 1, Predef.identity) shouldBe
        Set(
          Location(3, 4),
          Location(2, 4),
          Location(1, 4),
          Location(0, 4)
        )

      Queen(Black).tillNotAttacked(board, queenLocation.x, queenLocation.y, _ + 1, Predef.identity) shouldBe
        Set.empty
    }

    "stop on iterations limit" in {

      Queen(Black).tillNotAttacked(board, queenLocation.x, queenLocation.y, _ - 1, Predef.identity, max = 2) shouldBe
        Set(
          Location(3, 4),
          Location(2, 4)
        )
    }

  }
}
