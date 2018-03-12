package highrung.model

import highrung.model.EloResultType._
import org.specs2.mutable.Specification
import EloRatingDeviation._

class EloSpec extends Specification {

  "Elo rating deviation" should {
    "be correctly classified" in {
      EloRatingDeviation(SmallDeviationThreshold - 1) === SmallRatingDeviation
      EloRatingDeviation(MediumDeviationThreshold - 1) === MediumRatingDeviation
      EloRatingDeviation(MediumDeviationThreshold + 1) === LargeRatingDeviation
    }
  }

  "Elo result" should {

    def r(s: String) = MatchResult(s)

    "be correctly recognised for straight set win" in {
      EloResult(r("3:0 (9:0,9:0,9:0)")) === StraightSetWin
    }

    "be correctly recognised for strong win" in {
      EloResult(r("3:1 (9:0,9:0,0:9,9:0)")) === StrongWin
    }

    "be correctly recognised for weak win" in {
      EloResult(r("3:2 (9:0,9:0,0:9,0:9,9:0)")) === WeakWin
    }

    "be correctly recognised for straight set loss" in {
      EloResult(r("0:3 (0:9,0:9,0:9)")) === StraightSetLoss
    }

    "be correctly recognised for strong loss" in {
      EloResult(r("1:3 (0:9,0:9,9:0,0:9)")) === StrongLoss
    }

    "be correctly recognised for weak loss" in {
      EloResult(r("2:3 (0:9,0:9,9:0,9:0,0:9)")) === WeakLoss
    }
  }
}