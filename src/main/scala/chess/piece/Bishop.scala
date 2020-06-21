package chess.piece

import chess.Player
import chess.board.{Board, Location}

case class Bishop(override val player: Player) extends Piece {

  override def symbol: Char = 'b'

  override def availableAttackOrMove(board: Board, current: Location): Set[Location] = {
    tillNotAttacked(board, current.x, current.y, _ + 1, _ + 1) ++
      tillNotAttacked(board, current.x, current.y, _ - 1, _ - 1) ++
      tillNotAttacked(board, current.x, current.y, _ + 1, _ - 1) ++
      tillNotAttacked(board, current.x, current.y, _ - 1, _ + 1)
  }

  override def availableAttack(board: Board, current: Location): Set[Location] =
    availableAttackOrMove(board, current)
}
