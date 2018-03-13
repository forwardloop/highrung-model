package highrung.model

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
}