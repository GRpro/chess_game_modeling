package chess.board

case class Location(x: Int, y: Int) {
  def toStringCanonical: String = s"${Board.getCanonicalX(x)}${Board.getCanonicalY(y).toString}"
  override def toString: String = s"($x, $y)"
}
