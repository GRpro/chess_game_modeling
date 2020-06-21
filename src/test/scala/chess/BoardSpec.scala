package chess

import chess.board.{Board, Location}
import chess.piece.{Pawn, Queen}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BoardSpec extends AnyWordSpec with Matchers {


  "Board" should {

    "move" in {

      val board = Board.located(
        Map(
          Location(4, 6) -> Pawn(White)
        )
      )

      board.move(Location(4, 6), Location(4, 4)) shouldBe((
        Board.located(
          Map(
            Location(4, 4) -> Pawn(White)
          )
        ),
        None
      )
      )
    }

    "move and capture" in {

      val board = Board.located(
        Map(
          Location(4, 6) -> Pawn(White),
          Location(3, 5) -> Queen(Black)
        )
      )

      val (newBoard, capturedOpt) = board.move(Location(4, 6), Location(3, 5))

      newBoard.get(Location(3, 5)) shouldBe Some(Pawn(White))
      newBoard.get(Location(4, 6)) shouldBe None

      capturedOpt shouldBe Some(Queen(Black))
    }
  }
}
