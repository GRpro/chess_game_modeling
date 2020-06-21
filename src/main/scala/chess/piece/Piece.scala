package chess.piece

import chess.board._
import chess.{Black, GameState, Player, White}

trait Piece {

  def player: Player

  def symbol: Char

  final def displaySymbol: Char = player match {
    case White => symbol.toUpper
    case Black => symbol
  }

  def availableAttackOrMove(board: Board, current: Location): Set[Location]

  def availableAttack(board: Board, current: Location): Set[Location]

  def tillNotAttacked(board: Board, x: Int, y: Int, nextX: Int => Int, nextY: Int => Int, max: Int = 7): Set[Location] = {
    var hasAttacked: Boolean = false
    var iterationsLeft = max

    Iterator.iterate((nextX(x), nextY(y), isValidMoveOrAttack(board, nextX(x), nextY(y)))) {
      case (prevX, prevY, _) =>
        val nX = nextX(prevX)
        val nY = nextY(prevY)
        (nX, nY, isValidMoveOrAttack(board, nX, nY))
    }.takeWhile {
      case (_, _, (location, isValid)) if iterationsLeft > 0 =>
        if (isValid) {
          if (!hasAttacked) {
            if (isValidAttack(board, location.x, location.y)._2) {
              hasAttacked = true
            }
            iterationsLeft -= 1
            true
          } else {
            false
          }
        } else {
          false
        }
      case _ =>
        false
    }.map {
      case (_, _, (location, _)) =>
        location
    }.toList.toSet
  }

  def isValidMoveOrAttack(board: Board, x: Int, y: Int): (Location, Boolean) =
    Location(x, y) -> (Board.isOnBoard(x, y) && board.get(Location(x, y)).forall(_.player != player))

  // has opposite color piece on the way
  def isValidAttack(board: Board, x: Int, y: Int): (Location, Boolean) =
    Location(x, y) -> (Board.isOnBoard(x, y) && board.get(Location(x, y)).exists(_.player != player))

  // no piece on the way
  def isValidMove(board: Board, x: Int, y: Int): (Location, Boolean) =
    Location(x, y) -> (Board.isOnBoard(x, y) && board.get(Location(x, y)).map(_ => false).getOrElse(true))

  def tryMove(state: GameState, move: Move): MoveResult = {

    val canMove = availableAttackOrMove(state.board, move.src).contains(move.dest)

    if (player != state.currentPlayer) {
      Invalid(move)
    } else if (!canMove) {
      Invalid(move)
    } else {
      val (newBoard, capturedPieceOpt) = state.board.move(move.src, move.dest)

      // is check for me ?
      val checkFrom = newBoard.attackingKingPieces(player)

      if (checkFrom.nonEmpty) {
        InvalidLeavesInCheck(move, checkFrom)
      } else {
        // is check for opponent ?
        val checkFrom = newBoard.attackingKingPieces(player.next)

        val newState = player match {
          case White =>
            state.copy(board = newBoard, currentPlayer = Black, blackKingInCheck = checkFrom, whiteKingInCheckFrom = Set.empty)
          case Black =>
            state.copy(board = newBoard, currentPlayer = White, whiteKingInCheckFrom = checkFrom, blackKingInCheck = Set.empty)
        }

        Moved(move, newState, checkFrom, capturedPieceOpt)
      }
    }
  }
}


case class PieceLocation(piece: Piece, location: Location)
