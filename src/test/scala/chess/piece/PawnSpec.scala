package chess.piece

import chess.board.{Board, Location}
import chess.{Black, White}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PawnSpec extends AnyWordSpec with Matchers {

  "Pawn" should {

    "attack opponent" in {

      val pawnLocation = Location(4, 7)
      val board = Board.located(Map(
        pawnLocation -> Pawn(White),
        Location(3, 6) -> Pawn(Black),
        Location(5, 6) -> Queen(Black)
      ))

      Pawn(White).availableAttack(board, pawnLocation) shouldBe {
        Set(
          Location(3, 6),
          Location(5, 6)
        )
      }
    }

    "not attack empty or own pieces" in {

      val pawnLocation = Location(4, 7)
      val board = Board.located(Map(
        pawnLocation -> Pawn(White),
        Location(3, 6) -> Pawn(White)
      ))

      Pawn(White).availableAttack(board, pawnLocation) shouldBe
        Set.empty
    }

    "move" in {
      val board = Board.located(Map(
        Location(4, 6) -> Pawn(White),
        Location(3, 5) -> Pawn(White),
        Location(1, 6) -> Pawn(White),
        Location(1, 5) -> King(White),
        Location(5, 5) -> Queen(Black)
      ))

      //println(board)

      Pawn(White).availableAttackOrMove(board, Location(4, 6)) shouldBe
        Set(
          Location(4, 5),
          Location(4, 4),
          Location(5, 5)
        )

      Pawn(White).availableAttackOrMove(board, Location(3, 5)) shouldBe
        Set(
          Location(3, 4)
        )

      Pawn(White).availableAttackOrMove(board, Location(1, 6)) shouldBe
        Set.empty // Blocked by King
    }
  }
}
