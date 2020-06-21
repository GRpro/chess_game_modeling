package chess.board

import chess.GameState
import chess.piece.{Piece, PieceLocation}


sealed trait MoveResult

sealed trait ValidMoveResult extends MoveResult

sealed trait InvalidMoveResult extends MoveResult

case class Moved(move: Move, newState: GameState, checkFrom: Set[PieceLocation], captured: Option[Piece]) extends ValidMoveResult {
  override def toString: String = move.toString
}

// Invalid

// invalid coordinates or no src piece
// or cannot move existing piece that way
case class Invalid(move: Move) extends InvalidMoveResult {
  override def toString: String = String.format("Invalid move: %s", move.toString)
}

case class InvalidLeavesInCheck(move: Move, checkFrom: Set[PieceLocation]) extends InvalidMoveResult {
  override def toString: String = String.format("Invalid move: %s leaves %s in check position!", move.toString, "King")
}
