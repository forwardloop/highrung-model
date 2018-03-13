package highrung.model

import forwardloop.glicko2s.{ Draw, EloResult }

/*
 * Extended definitions of `EloResult`s to tailor to racquet sports, to differentiate, say, 3:2 win from 3:0
 * in order to refine ELO rating computations
 *
 * @see <a href="https://github.com/forwardloop/glicko2s/blob/master/src/main/scala/forwardloop/glicko2s/Glicko2.scala">Glicko2#EloResult</a>
 */
object RacquetEloResult {
  final val StrongWinVal = 0.9
  final val WeakWinVal = 0.8

  final val StraightSetResultStrength = 3.toByte
  final val StrongResultStrength = 2.toByte
  final val WeakResultStrength = 1.toByte

  import RacquetEloResultType._

  def apply(matchResult: MatchResult): EloResult = {

    def computeStrength(p1: Int, p2: Int): Int =
      if (Math.abs(p1 - p2) == 1) WeakResultStrength
      else if (Math.abs(p1 - p2) >= 2 && p1 != 0 && p2 != 0) StrongResultStrength
      else StraightSetResultStrength

    def toEloResult(score: Score): EloResult = score match {
      case Score(p1, p2) if p1 == p2 => Draw
      case Score(p1, p2) =>
        val winLoss = if (p1 > p2) Win else Loss
        val strength = computeStrength(p1, p2)
        RacquetEloResultType
          .eloResults
          .find(r => r.winLoss == winLoss && r.strength == strength)
          .getOrElse(EloUndefined)
      case _ => EloUndefined
    }

    matchResult match {
      case Overall(score) => toEloResult(score)
      case Detailed(games) => toEloResult(MatchResult.gamesToScore(games))
      case _ => EloUndefined
    }
  }
}

import RacquetEloResult._

trait WinLoss { def reverse: WinLoss }
case object Win extends WinLoss { def reverse = Loss }
case object Loss extends WinLoss { def reverse = Win }

trait ResultStrength extends EloResult { def strength: Byte }
trait WinLossMarker extends EloResult { def winLoss: WinLoss }
trait RacquetEloResult extends EloResult with ResultStrength with WinLossMarker

trait StraightSetResult extends ResultStrength { def strength = StraightSetResultStrength }
trait StrongResult extends ResultStrength { def strength = StrongResultStrength }
trait WeakResult extends ResultStrength { def strength = WeakResultStrength }

trait WinMarker extends WinLossMarker { def winLoss = Win }
trait LossMarker extends WinLossMarker { def winLoss = Loss }

object RacquetEloResultType {

  case object StraightSetWin extends RacquetEloResult
    with StraightSetResult with WinMarker { val value = 1.0 } //e.g. 3:0

  case object StrongWin extends RacquetEloResult
    with StrongResult with WinMarker { val value = StrongWinVal } //e.g. 3:1

  case object WeakWin extends RacquetEloResult
    with WeakResult with WinMarker { val value = WeakWinVal } //e.g. 3:2

  case object StraightSetLoss extends RacquetEloResult
    with StraightSetResult with LossMarker { val value = 1.0 - StraightSetWin.value } //e.g. 0:3

  case object StrongLoss extends RacquetEloResult
    with StrongResult with LossMarker { val value = 1.0 - StrongWin.value } //e.g. 1:3

  case object WeakLoss extends RacquetEloResult
    with WeakResult with LossMarker { val value = 1.0 - WeakWin.value } //e.g. 2:3

  case object EloUndefined extends EloResult { val value = -1.0 }

  val eloResults = Seq(StraightSetWin, StrongWin, WeakWin, StraightSetLoss, StrongLoss, WeakLoss)
}