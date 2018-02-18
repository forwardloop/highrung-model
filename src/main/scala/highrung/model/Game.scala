package highrung.model

case class Game(points1: Int, points2: Int) {

  require(points1 >= 0 && points2 >= 0, s"Game points must be naught or positive: $points1, $points2")

  def player1Lost() = points1 < points2

  def player2Lost() = points1 > points2

  def isDraw() = points1 == points2
}