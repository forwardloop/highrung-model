package highrung.model

import org.specs2.mutable._

class MatchResultSpec extends Specification {

  "parse match result" should {

    "produce 1 game from 1:0 (1:0)" in {
      val games = List(Game(1, 0))
      MatchResult("1:0 (1:0)") must beEqualTo(Detailed(games))
    }

    "produce 2 games from 2:0 (1:0,9:0) " in {
      val games = List(Game(1, 0), Game(9, 0))
      MatchResult("2:0 (1:0,9:0)") must beEqualTo(Detailed(games))
    }

    "produce 3 games from 1:2 (1:0,0:9,4:5)" in {
      val games = List(Game(1, 0), Game(0, 9), Game(4, 5))
      MatchResult("1:2 (1:0,0:9,4:5)") must beEqualTo(Detailed(games))
    }

    "produce Undefined for a 'forfeit'" in {
      MatchResult("forfeit") must beEqualTo(Undefined)
    }

    "produce Undefined for result with incorrect format" in {
      MatchResult("2:0 (1:a,9:0)") must beEqualTo(Undefined)
    }

    "produce score 3:0 a match without detailed scores like 3:0" in {
      val score = Score(3, 0)
      MatchResult("3:0") must beEqualTo(Overall(score))
    }

    "not produce score for '3:a' (wrong format)" in {
      MatchResult("3:a") must beEqualTo(Undefined)
    }

    "not produce score for 'aaa:aaa' (wrong format)" in {
      MatchResult("aaa:aaa") must beEqualTo(Undefined)
    }

    "produce forfeit with player1 as beneficiary for 'f1'" in {
      MatchResult("f1") must beEqualTo(Forfeit(1))
    }

    "produce forfeit with player2 as beneficiary for 'f2'" in {
      MatchResult("f2") must beEqualTo(Forfeit(2))
    }

    "not produce forfeit for 'f3'" in {
      MatchResult("f3") must beEqualTo(Undefined)
    }

    "not produce forfeit for 'ff'" in {
      MatchResult("ff") must beEqualTo(Undefined)
    }
  }
}
