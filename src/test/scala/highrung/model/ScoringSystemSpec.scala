package highrung.model

import org.specs2.mutable.Specification

class ScoringSystemSpec extends Specification {

  val er = EnglishSquashScoring

  "English squash scoring system" should {
    "pass for 9:0" in {
      er.validate(MatchResult("1:0 (9:0)")) must beTrue
    }
    "pass for 9:0, 9:7" in {
      er.validate(MatchResult("2:0 (9:0,9:7)")) must beTrue
    }
    "pass for 0:9, 7:9" in {
      er.validate(MatchResult("0:2 (0:9,7:9)")) must beTrue
    }
    "pass for 10:9, 10:8, 9:10" in {
      er.validate(MatchResult("2:1 (10:9,10:8,9:10)")) must beTrue
    }
    "fail for 8:0" in {
      er.validate(MatchResult("1:0 (8:0)")) must beFalse
    }
    "fail for 10:0" in {
      er.validate(MatchResult("1:0 (10:0)")) must beFalse
    }
    "fail for 11:9" in {
      er.validate(MatchResult("1:0 (11:9)")) must beFalse
    }
    "fail for 9:0, 8:0" in {
      er.validate(MatchResult("2:0 (9:0,8:0)")) must beFalse
    }
    "fail for 0:8, 0:9" in {
      er.validate(MatchResult("0:2 (0:8,0:9)")) must beFalse
    }
    "fail for -1:1" in {
      er.validate(MatchResult("0:1 (-1:1)")) must beFalse
    }
    "pass for forfeit" in {
      er.validate(MatchResult("f1")) must beTrue
    }
    "fail for overall result" in {
      er.validate(MatchResult("1:0")) must beFalse
    }
  }

  val pars = PointARallySquashScoring

  "Point-a-rally squash scoring system" should {
    "pass for 11:0" in {
      pars.validate(MatchResult("1:0 (11:0)")) must beTrue
    }
    "pass for 11:0, 11:9" in {
      pars.validate(MatchResult("2:0 (11:0,11:9)")) must beTrue
    }
    "pass for 0:11, 9:11" in {
      pars.validate(MatchResult("0:2 (0:11,9:11)")) must beTrue
    }
    "pass for 15:13, 23:25, 20:18" in {
      pars.validate(MatchResult("3:0 (15:13,23:25,20:18)")) must beTrue
    }
    "fail for 9:0" in {
      pars.validate(MatchResult("1:0 (9:0)")) must beFalse
    }
    "fail for 10:0" in {
      pars.validate(MatchResult("1:0 (10:0)")) must beFalse
    }
    "fail for 11:10" in {
      pars.validate(MatchResult("1:0 (11:10)")) must beFalse
    }
    "fail for 11:0, 10:0" in {
      pars.validate(MatchResult("1:0 (11:0,10:0)")) must beFalse
    }
    "fail for 0:10, 0:11" in {
      pars.validate(MatchResult("0:2 (0:10,0:11)")) must beFalse
    }
    "fail for -1:1" in {
      pars.validate(MatchResult("0:1 (-1:1)")) must beFalse
    }
    "pass for forfeit" in {
      pars.validate(MatchResult("f1")) must beTrue
    }
    "fail for overall result" in {
      pars.validate(MatchResult("1:0")) must beFalse
    }
  }

  val any = AnySquashScoring

  "Anything goes squash scoring system" should {
    "pass for 300:0" in {
      any.validate(MatchResult("1:0 (300:0)")) must beTrue
    }
  }

  "rating system with abbreviation N" should {
    "be recognised as AnySquashScoring" in { SquashScoring("N") must beEqualTo(AnySquashScoring) }
  }

  val desc = "None (any scores accepted)"

  "rating system with description " + desc should {
    "be recognised as AnySquashScoring" in { SquashScoring.fromDescription(desc) must beEqualTo(AnySquashScoring) }
  }
}