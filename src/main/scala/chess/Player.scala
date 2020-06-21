package chess

sealed trait Player {
  def next: Player
}

case object Black extends Player {
  override def next: Player = White
  override def toString: String = "Black"
}
case object White extends Player {
  override def next: Player = Black
  override def toString: String = "White"
}
