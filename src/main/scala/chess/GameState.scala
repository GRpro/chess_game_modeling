package chess

import chess.board.Board
import chess.piece.PieceLocation

case class GameState(board: Board,
                     currentPlayer: Player,
                     whiteKingInCheckFrom: Set[PieceLocation],
                     blackKingInCheck: Set[PieceLocation])
