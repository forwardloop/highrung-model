package highrung.model

import org.joda.time.DateTime

case class Elo(
  id: Option[Int],
  playerId: Int,
  matchId: Option[Int],
  glickoRating: Double,
  glickoRatingDeviation: Double,
  glickoVolatility: Double,
  createdTs: DateTime)

object EloRatingDeviation {
  final val SmallDeviationThreshold = 80
  final val MediumDeviationThreshold = 150

  def apply(ratingDeviation: Double,
    smallDeviationThreshold: Int = SmallDeviationThreshold,
    mediumDeviationThreshold: Int = MediumDeviationThreshold) = ratingDeviation match {
    case rd if rd < smallDeviationThreshold => SmallRatingDeviation
    case rd if rd < mediumDeviationThreshold => MediumRatingDeviation
    case _ => LargeRatingDeviation
  }
}

sealed trait EloRatingDeviation
case object SmallRatingDeviation extends EloRatingDeviation
case object MediumRatingDeviation extends EloRatingDeviation
case object LargeRatingDeviation extends EloRatingDeviation