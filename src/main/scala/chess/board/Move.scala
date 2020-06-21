package chess.board

case class Move(src: Location, dest: Location) {
  override def toString: String = s"(${src.toStringCanonical} -> ${dest.toStringCanonical})($src -> $dest)"
}
