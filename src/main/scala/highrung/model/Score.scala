package highrung.model

case class Score(gamesP1: Int, gamesP2: Int) {
  require(gamesP1 >= 0 && gamesP2 >= 0, s"Games have to be naught or positive: $gamesP1, $gamesP2")
}