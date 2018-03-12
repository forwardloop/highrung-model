package highrung.model

import scala.math.min

object LeaguePoints {

  final val NoPoints = Seq(0, 0)
  final val ForfeitPoints = Seq(3, 0)

  def compute(mr: MatchResult): Seq[Int] = mr match {
    case Overall(score) => rateScore(score)
    case Detailed(games) => rateScore(MatchResult.gamesToScore(games))
    case Forfeit(pId) => pId match {
      case 1 => ForfeitPoints
      case 2 => ForfeitPoints.reverse
      case _ => NoPoints
    }
    case _ => NoPoints
  }

  private def rateScore(score: Score): Seq[Int] = {

    def ratePlayer(gamesWon: Int) = gamesWon match {
      case n if n > 0 => min(n * 2, 6)
      case m if m == 0 => 1
      case _ => 0
    }

    score
      .toSeq()
      .map(ratePlayer(_))
  }
}