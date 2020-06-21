package chess.board

import chess.Player
import chess.piece.{Bishop, King, Knight, Pawn, Piece, PieceLocation, Queen, Rook}

case class BoardImpl(location2piece: Map[Location, Piece], piece2location: Map[Piece, Set[Location]]) extends Board {

  import Board._

  private def get(piece: Piece): Set[Location] =
    piece2location.withDefaultValue(Set.empty)(piece)

  def get(location: Location): Option[Piece] =
    location2piece.get(location)

  override def toString: String = {
    var res = ""

    val filesRow = " " * 4 + horizontal.map { case (_, file) => file }.mkString(" " * 3)
    val separatorRow = """  +---+---+---+---+---+---+---+---+"""

    res += filesRow + "\n"
    res += separatorRow + "\n"

    //"\u001B[40mA\u001b[39m"

    vertical.foreach { case (y, rank) =>
      val line =
        "%d |".format(rank) +
          horizontal
            .map { case (x, file) =>
              " %s |".format(
                location2piece.get(Location(x, y))
                  .map(_.displaySymbol)
                  .getOrElse(" ")
              )
            }
            .reduceLeft(_ + _) +
          " %d".format(rank)

      res += line + "\n"
      res += separatorRow + "\n"
    }

    res += filesRow + "\n\n"
    res
  }


  def withPieceRemoved(location: Location): (Board, Option[Piece]) = {
    val l2p = location2piece
    val p2l = piece2location

    l2p.get(location)
      .map { piece =>

        val newBoard: Board = BoardImpl(
          location2piece = l2p - location,
          piece2location = p2l + (piece -> (p2l(piece) - location))
        )

        newBoard -> Some(piece)
      }
      .getOrElse(this, None)
  }

  def withPieceAdded(piece: Piece, location: Location): Board = {
    val l2p = location2piece
    val p2l = piece2location

    val newBoard: Board = BoardImpl (
      location2piece = {
        l2p + (location -> piece)
      },
      piece2location = {
        val locations = p2l(piece)
        p2l + (piece -> (locations + location))
      }
    )

    newBoard
  }


  def attackingKingPieces(player: Player): Set[PieceLocation] = {
    val kingLocation = get(King(player)).head

    val oppositePlayer = player.next

    val piecesThatCanMakeCheck = Set(
      Queen(oppositePlayer),
      Rook(oppositePlayer),
      Bishop(oppositePlayer),
      Knight(oppositePlayer),
      Pawn(oppositePlayer)
    )

    piecesThatCanMakeCheck
      .map { piece =>
        piece -> get(piece)
          .map { location =>
            location -> piece.availableAttack(this, location).contains(kingLocation)
          }
          .collect { case (location, makesCheck) if makesCheck =>
            location
          }
      }
      .flatMap {
        case (piece, locations) =>
          locations.map { PieceLocation(piece, _) }
      }
  }

}
