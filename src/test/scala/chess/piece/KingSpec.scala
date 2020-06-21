package chess.piece

import chess.board.{Board, Location}
import chess.{Black, White}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class KingSpec extends AnyWordSpec with Matchers {

  "King" should {

    "be in check" in {

      val board = Board.located(
        Map(
          Location(3, 3) -> King(Black),
          Location(5, 4) -> Knight(White),
          Location(4, 4) -> Pawn(White),
          Location(6, 6) -> Queen(White) // no attack for King
        )
      )

      board.attackingKingPieces(Black) shouldBe
        Set(
          PieceLocation(Pawn(White), Location(4, 4)),
          PieceLocation(Knight(White), Location(5, 4))
        )
    }

    "not be in check and move" in {
      val board = Board.located(
        Map(
          Location(3, 3) -> King(Black),
          Location(2, 2) -> Pawn(White),
          Location(4, 2) -> Pawn(White),
          Location(4, 3) -> Bishop(Black)
        )
      )

      board.attackingKingPieces(Black) shouldBe Set.empty

      King(Black).availableAttackOrMove(board, Location(3, 3)) shouldBe
        Set(
          Location(2, 2),
          Location(3, 2),
          Location(4, 2),
          Location(4, 4),
          Location(3, 4),
          Location(2, 4),
          Location(2, 3),
        )
    }
  }
}
