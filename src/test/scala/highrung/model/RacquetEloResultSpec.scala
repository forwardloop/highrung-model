package highrung.model

import highrung.model.RacquetEloResultType._
import org.specs2.mutable.Specification

class RacquetEloResultSpec extends Specification {

  "MatchResult" should {

    def r(s: String) = MatchResult(s)

    /* detailed results */

    "be correctly recognised as ELO straight set win 3:0 (9:0,9:0,9:0)" in {
      RacquetEloResult(r("3:0 (9:0,9:0,9:0)")) === StraightSetWin
    }

    "be correctly recognised as ELO strong win 3:1 (9:0,9:0,0:9,9:0)" in {
      RacquetEloResult(r("3:1 (9:0,9:0,0:9,9:0)")) === StrongWin
    }

    "be correctly recognised as ELO weak win 3:2 (9:0,9:0,0:9,0:9,9:0)" in {
      RacquetEloResult(r("3:2 (9:0,9:0,0:9,0:9,9:0)")) === WeakWin
    }

    "be correctly recognised as ELO straight set loss 0:3 (0:9,0:9,0:9)" in {
      RacquetEloResult(r("0:3 (0:9,0:9,0:9)")) === StraightSetLoss
    }

    "be correctly recognised as ELO strong loss 1:3 (0:9,0:9,9:0,0:9)" in {
      RacquetEloResult(r("1:3 (0:9,0:9,9:0,0:9)")) === StrongLoss
    }

    "be correctly recognised as ELO weak loss 2:3 (0:9,0:9,9:0,9:0,0:9)" in {
      RacquetEloResult(r("2:3 (0:9,0:9,9:0,9:0,0:9)")) === WeakLoss
    }

    /* overall results */

    "be correctly recognised as ELO straight set win 3:0" in {
      RacquetEloResult(r("3:0")) === StraightSetWin
    }

    "be correctly recognised as ELO straight set win 10:0" in {
      RacquetEloResult(r("10:0")) === StraightSetWin
    }

    "be correctly recognised as ELO strong win 3:1" in {
      RacquetEloResult(r("3:1")) === StrongWin
    }

    "be correctly recognised as ELO strong win 5:3" in {
      RacquetEloResult(r("5:3")) === StrongWin
    }

    "be correctly recognised as ELO strong win 5:2" in {
      RacquetEloResult(r("5:2")) === StrongWin
    }

    "be correctly recognised as ELO strong win 5:1" in {
      RacquetEloResult(r("5:1")) === StrongWin
    }

    "be correctly recognised as ELO weak win 3:2" in {
      RacquetEloResult(r("3:2")) === WeakWin
    }

    "be correctly recognised as ELO weak win 5:4" in {
      RacquetEloResult(r("5:4")) === WeakWin
    }

    "be correctly recognised as ELO straight set loss 0:3" in {
      RacquetEloResult(r("0:3")) === StraightSetLoss
    }

    "be correctly recognised as ELO strong loss 1:3" in {
      RacquetEloResult(r("1:3")) === StrongLoss
    }

    "be correctly recognised as ELO weak loss 2:3" in {
      RacquetEloResult(r("2:3")) === WeakLoss
    }

    "not be recognised for forfeit f1" in {
      RacquetEloResult(r("f1")) === EloUndefined
    }
  }
}