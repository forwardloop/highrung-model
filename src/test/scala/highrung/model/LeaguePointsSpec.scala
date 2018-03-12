package highrung.model

import org.specs2.mutable._

class LeaguePointsSpec extends Specification {

  "award leagues points for result" should {

    def r(s: String) = MatchResult(s)

    "produce (2,1) league points for match result 1:0 (1:0)" in {
      LeaguePoints.compute(r("1:0 (1:0)")) must beEqualTo(Seq(2, 1))
    }

    "produce (4,1) league points for match result 2:0 (1:0,9:0)" in {
      LeaguePoints.compute(r("2:0 (1:0,9:0)")) must beEqualTo(Seq(4, 1))
    }

    "produce (2,4) league points for match result 1:2 (1:0,0:9,4:5)" in {
      LeaguePoints.compute(r("1:2 (1:0,0:9,4:5)")) must beEqualTo(Seq(2, 4))
    }

    "produce (4,4) league points for match result 2:2 (1:0,9:9,4:5)" in {
      LeaguePoints.compute(r("2:2 (1:0,9:9,4:5)")) must beEqualTo(Seq(4, 4))
    }

    "produce (6,1) league points for match result 3:0" in {
      LeaguePoints.compute(r("3:0")) must beEqualTo(Seq(6, 1))
    }

    "produce (1,6) league points for match result 0:3" in {
      LeaguePoints.compute(r("0:3")) must beEqualTo(Seq(1, 6))
    }

    "produce (3,0) league points for forfeit for player 1" in {
      LeaguePoints.compute(r("f1")) must beEqualTo(Seq(3, 0))
    }

    "produce (0,3) league points for forfeit for player 2" in {
      LeaguePoints.compute(r("f2")) must beEqualTo(Seq(0, 3))
    }

    "produce (0,0) league points for undefined result" in {
      LeaguePoints.compute(r("error")) must beEqualTo(Seq(0, 0))
    }
  }
}
