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

  "MatchResult" should {

    def r(s: String) = MatchResult(s)

    /* detailed results */

    "be correctly recognised as ELO straight set win 3:0 (9:0,9:0,9:0)" in {
      EloResult(r("3:0 (9:0,9:0,9:0)")) === StraightSetWin
    }

    "be correctly recognised as ELO strong win 3:1 (9:0,9:0,0:9,9:0)" in {
      EloResult(r("3:1 (9:0,9:0,0:9,9:0)")) === StrongWin
    }

    "be correctly recognised as ELO weak win 3:2 (9:0,9:0,0:9,0:9,9:0)" in {
      EloResult(r("3:2 (9:0,9:0,0:9,0:9,9:0)")) === WeakWin
    }

    "be correctly recognised as ELO straight set loss 0:3 (0:9,0:9,0:9)" in {
      EloResult(r("0:3 (0:9,0:9,0:9)")) === StraightSetLoss
    }

    "be correctly recognised as ELO strong loss 1:3 (0:9,0:9,9:0,0:9)" in {
      EloResult(r("1:3 (0:9,0:9,9:0,0:9)")) === StrongLoss
    }

    "be correctly recognised as ELO weak loss 2:3 (0:9,0:9,9:0,9:0,0:9)" in {
      EloResult(r("2:3 (0:9,0:9,9:0,9:0,0:9)")) === WeakLoss
    }

    /* overall results */

    "be correctly recognised as ELO straight set win 3:0" in {
      EloResult(r("3:0")) === StraightSetWin
    }

    "be correctly recognised as ELO straight set win 10:0" in {
      EloResult(r("10:0")) === StraightSetWin
    }

    "be correctly recognised as ELO strong win 3:1" in {
      EloResult(r("3:1")) === StrongWin
    }

    "be correctly recognised as ELO strong win 5:3" in {
      EloResult(r("5:3")) === StrongWin
    }

    "be correctly recognised as ELO strong win 5:2" in {
      EloResult(r("5:2")) === StrongWin
    }

    "be correctly recognised as ELO strong win 5:1" in {
      EloResult(r("5:1")) === StrongWin
    }

    "be correctly recognised as ELO weak win 3:2" in {
      EloResult(r("3:2")) === WeakWin
    }

    "be correctly recognised as ELO weak win 5:4" in {
      EloResult(r("5:4")) === WeakWin
    }

    "be correctly recognised as ELO straight set loss 0:3" in {
      EloResult(r("0:3")) === StraightSetLoss
    }

    "be correctly recognised as ELO strong loss 1:3" in {
      EloResult(r("1:3")) === StrongLoss
    }

    "be correctly recognised as ELO weak loss 2:3" in {
      EloResult(r("2:3")) === WeakLoss
    }

    "not be recognised for forfeit f1" in {
      EloResult(r("f1")) === EloUndefined
    }
  }
}