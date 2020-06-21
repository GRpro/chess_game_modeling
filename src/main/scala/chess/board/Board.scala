package chess.board

import chess.{Black, GameState, Player, White}
import chess.piece.{Bishop, King, Knight, Pawn, Piece, PieceLocation, Queen, Rook}

trait Board {

  def get(location: Location): Option[Piece]

  def withPieceAdded(piece: Piece, location: Location): Board

  def withPieceRemoved(location: Location): (Board, Option[Piece])

  // find opponent pieces that attack King of player
  def attackingKingPieces(player: Player): Set[PieceLocation]

  def move(src: Location, dest: Location): (Board, Option[Piece]) = {
    val (board, capturedPieceOpt) = withPieceRemoved(dest)

    val (board1, removedPieceOpt) = board.withPieceRemoved(src)

    val resultBoard = board1.withPieceAdded(removedPieceOpt.get, dest)

    resultBoard -> capturedPieceOpt
  }
}


object Board {

  lazy val horizontal: Seq[(Int, Char)] = (0 to 7) zip ('A' to 'H')
  lazy val vertical: Seq[(Int, Int)] = (0 to 7) zip (1 to 8).reverse

  lazy val getCanonicalX: Int => Char = {
    val map = horizontal.toMap
    map(_)
  }

  lazy val getCanonicalY: Int => Int = {
    val map = vertical.toMap
    map(_)
  }

  def isOnBoard(x: Int, y: Int): Boolean = {
    !(x > 7 || x < 0 || y > 7 || y < 0)
  }

  def move(state: GameState, move: Move): MoveResult = {
    if (!isOnBoard(move.src.x, move.src.y) || !isOnBoard(move.dest.x, move.dest.y)) {
      Invalid(move)
    } else {
      state.board.get(move.src)
        .map { piece =>
          piece.tryMove(state, move)
        }
        .getOrElse(Invalid(move))
    }
  }


  def located(locations: Map[Location, Piece]): Board = {
    BoardImpl(
      location2piece = locations,
      piece2location = locations.groupBy {
        case (_, piece) => piece
      }
        .mapValues { _.keySet }
    )
  }

  def initial(): Board = {

    val wKing = King(White)
    val wQueen = Queen(White)
    val wRook = Rook(White)
    val wKnight = Knight(White)
    val wBishop = Bishop(White)
    val wPawn = Pawn(White)

    val bKing = King(Black)
    val bQueen = Queen(Black)
    val bRook = Rook(Black)
    val bKnight = Knight(Black)
    val bBishop = Bishop(Black)
    val bPawn = Pawn(Black)

    def placePawns(row: Int, pawn: => Pawn): Seq[(Location, Piece)] =
      (0 to 7).map(i => Location(i, row) -> pawn)

    val locations = Map(
      Location(0, 0) -> bRook,
      Location(1, 0) -> bKnight,
      Location(2, 0) -> bBishop,
      Location(3, 0) -> bQueen,
      Location(4, 0) -> bKing,
      Location(5, 0) -> bBishop,
      Location(6, 0) -> bKnight,
      Location(7, 0) -> bRook,
      Location(0, 7) -> wRook,
      Location(1, 7) -> wKnight,
      Location(2, 7) -> wBishop,
      Location(3, 7) -> wQueen,
      Location(4, 7) -> wKing,
      Location(5, 7) -> wBishop,
      Location(6, 7) -> wKnight,
      Location(7, 7) -> wRook
    ) ++ placePawns(1, bPawn) ++ placePawns(6, wPawn)

    located(locations)
  }
}