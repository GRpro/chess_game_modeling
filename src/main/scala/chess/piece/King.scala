package chess.piece

import chess._
import chess.board.{Board, Location}


case class King(override val player: Player) extends Piece  {
  override def symbol: Char = 'k'

  def availableAttackOrMove(board: Board, current: Location): Set[Location] = {
    Set(
      isValidMoveOrAttack(board, current.x + 1, current.y),
      isValidMoveOrAttack(board, current.x + 1, current.y + 1),
      isValidMoveOrAttack(board, current.x + 1, current.y - 1),
      isValidMoveOrAttack(board, current.x - 1, current.y),
      isValidMoveOrAttack(board, current.x - 1, current.y + 1),
      isValidMoveOrAttack(board, current.x - 1, current.y - 1),
      isValidMoveOrAttack(board, current.x, current.y + 1),
      isValidMoveOrAttack(board, current.x, current.y - 1)
    ).collect {
      case (location, isValid) if isValid =>
        location
    }
  }

  def availableAttack(board: Board, current: Location): Set[Location] =
    availableAttackOrMove(board, current)
}
