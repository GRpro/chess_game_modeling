package chess.piece

import chess.Player
import chess.board.{Board, Location}

case class Knight(override val player: Player) extends Piece {
  override def symbol: Char = 'n'

  override def availableAttackOrMove(board: Board, current: Location): Set[Location] = {
    Set(
      isValidMoveOrAttack(board, current.x + 2, current.y + 1),
      isValidMoveOrAttack(board, current.x + 2, current.y - 1),
      isValidMoveOrAttack(board, current.x - 2, current.y + 1),
      isValidMoveOrAttack(board, current.x - 2, current.y - 1),
      isValidMoveOrAttack(board, current.x + 1, current.y + 2),
      isValidMoveOrAttack(board, current.x - 1, current.y + 2),
      isValidMoveOrAttack(board, current.x + 1, current.y - 2),
      isValidMoveOrAttack(board, current.x - 1, current.y - 2)
    ).collect {
      case (location, isValid) if isValid =>
        location
    }
  }

  override def availableAttack(board: Board, current: Location): Set[Location] =
    availableAttackOrMove(board, current)

}