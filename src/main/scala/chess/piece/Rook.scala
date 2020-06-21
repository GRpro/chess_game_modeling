package chess.piece

import chess.Player
import chess.board.{Board, Location}

case class Rook(override val player: Player) extends Piece {
  override def symbol: Char = 'r'

  override def availableAttackOrMove(board: Board, current: Location): Set[Location] = {
    tillNotAttacked(board, current.x, current.y, x => x, _ + 1) ++
      tillNotAttacked(board, current.x, current.y, x => x, _ - 1) ++
      tillNotAttacked(board, current.x, current.y, _ + 1, y => y) ++
      tillNotAttacked(board, current.x, current.y, _ - 1, y => y)
  }

  override def availableAttack(board: Board, current: Location): Set[Location] =
    availableAttackOrMove(board, current)
}