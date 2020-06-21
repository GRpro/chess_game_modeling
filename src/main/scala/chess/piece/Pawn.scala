package chess.piece

import chess._
import chess.board.{Board, Location}

case class Pawn(override val player: Player) extends Piece {
  override def symbol: Char = 'p'

  override def availableAttackOrMove(board: Board, current: Location): Set[Location] = {
    val result = (player match {
      case White if current.y == 6 =>
        // white pawn hasn't been in move yet
        tillNotAttacked(board, current.x, current.y, x => x, _ - 1, max = 2)
          .map(_ -> true)
      case White =>
        Set(isValidMove(board, current.x, current.y - 1))
      case Black if current.y == 1 =>
        // black pawn hasn't been in move yet
        tillNotAttacked(board, current.x, current.y, x => x, _ + 1, max = 2)
          .map(_ -> true)
      case Black =>
        Set(isValidMove(board, current.x, current.y + 1))

    }).collect {
      case (location, isValid) if isValid =>
        location
    }

    // attack cases

    result ++ availableAttack(board, current)
  }

  override def availableAttack(board: Board, current: Location): Set[Location] = {
    (player match {
      case White =>
        Set(
          isValidAttack(board, current.x + 1, current.y - 1),
          isValidAttack(board, current.x - 1, current.y - 1)
        )
      case Black =>
        Set(
          isValidAttack(board, current.x + 1, current.y + 1),
          isValidAttack(board, current.x - 1, current.y + 1)
        )
    }).collect {
      case (location, isValid) if isValid =>
        location
    }
  }
}