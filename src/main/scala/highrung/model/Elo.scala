package highrung.model

import forwardloop.glicko2s.{ Draw, EloResult }
import org.joda.time.DateTime

case class Elo(
  id: Option[Int],
  playerId: Int,
  matchId: Option[Int],
  glickoRating: Double,
  glickoRatingDeviation: Double,
  glickoVolatility: Double,
  createdTs: DateTime)

/* Classification of ELO rating deviation in Glicko1 scale */
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

/*
 * Extended definitions of `ELOResult`s to tailor to squash, to differentiate, say, 3:2 win from 3:0
 *
 * @see <a href="https://github.com/forwardloop/glicko2s/blob/master/src/main/scala/forwardloop/glicko2s/Glicko2.scala">Glicko2#EloResult</a>
 */
object EloResult {
  final val StringWinVal = 0.9
  final val WeakWinVal = 0.8

  import EloResultType._

  //  private def opponentResult(eloRes: EloResult) = eloRes match {
  //    case Win =>
  //
  //  }

  def apply(matchResult: MatchResult): EloResult = {
    matchResult match {
      case Forfeit(pId) => EloUndefined
      case Overall(score) => EloUndefined
      case Detailed(games) =>
        MatchResult.gamesToScore(games) match {
          case Score(p1, p2) =>
            if (p1 == p2) Draw
            else if (p1 > p2) {
              if (p2 == 0) StraightSetWin
              else if (p1 == p2 + 1) WeakWin
              else StrongWin
            } else {
              if (p1 == 0) StraightSetLoss
              else if (p2 == p1 + 1) WeakLoss
              else StrongLoss
            }
          case _ => EloUndefined
        }
      case Undefined => EloUndefined
    }
  }
}

import EloResult._

trait StraightSetResult extends EloResult
trait StrongResult extends EloResult
trait WeakResult extends EloResult
trait AWin extends EloResult
trait ALoss extends EloResult

object EloResultType {
  case object StraightSetWin extends StraightSetResult with AWin { val value = 1.0 } //e.g. 3:0
  case object StrongWin extends StrongResult with AWin { val value = StringWinVal } //e.g. 3:1
  case object WeakWin extends WeakResult with AWin { val value = WeakWinVal } //e.g. 3:2
  case object StraightSetLoss extends StraightSetResult with ALoss { val value = 1.0 - StraightSetWin.value } //e.g. 0:3
  case object StrongLoss extends StrongResult with ALoss { val value = 1.0 - StrongWin.value } //e.g. 1:3
  case object WeakLoss extends WeakResult with ALoss { val value = 1.0 - WeakWin.value } //e.g. 2:3
  case object EloUndefined extends EloResult { val value = -1.0 }

  val eloResults = Seq(StraightSetWin, StrongWin, WeakWin, StraightSetLoss, WeakLoss, EloUndefined)
}