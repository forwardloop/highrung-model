package highrung.model

sealed trait ScoringSystem[T <: RacquetSport] {
  def abbreviation: String
  def description: String
  def validate(matchResult: MatchResult): Boolean
}

abstract class SquashScoring(val abbreviation: String, val description: String) extends ScoringSystem[Squash.type]

case object EnglishSquashScoring extends SquashScoring("E", "English/hand-out, to 9") {

  private def validateGame(g: Game): Boolean =
    ((g.points1 == 10) && g.points2 >= 8 && g.points2 <= 9) ||
    ((g.points1 == 9) && g.points2 >= 0 && g.points2 <= 8)  ||
    ((g.points2 == 10) && g.points1 >= 8 && g.points1 <= 9) ||
    ((g.points2 == 9) && g.points1 >= 0 && g.points1 <= 8)

  def validate(matchResult: MatchResult) = matchResult match {
    case Detailed(games) => games.foldLeft(true)((acc, game) => (acc && validateGame(game)))
    case _ => true
  }
}

case object PointARallySquashScoring extends SquashScoring("P", "Point-A-Rally, to 11") {

  private def validateGame(g: Game): Boolean =
    ((g.points1 == 11) && g.points2 >= 0 && g.points2 <= 9) ||
    ((g.points1 > 11) && g.points2 == (g.points1 - 2)) ||
    ((g.points2 == 11) && g.points1 >= 0 && g.points1 <= 9) ||
    ((g.points2 > 11) && g.points1 == (g.points2 - 2))

  def validate(matchResult: MatchResult) = matchResult match {
    case Detailed(games) => games.foldLeft(true)((acc, game) => (acc && validateGame(game)))
    case _ => true
  }
}

case object AnySquashScoring extends SquashScoring("N", "None (any scores accepted)") {
  def validate(matchResult: MatchResult) = true
}

object SquashScoring {

  def apply(abbreviation: String) =
    squashScoringSystems
      .find(_.abbreviation == abbreviation)
      .getOrElse(AnySquashScoring)

  def fromDescription(d: String) =
    squashScoringSystems
      .find(_.description == d)
      .getOrElse(AnySquashScoring)

  val squashScoringSystems = Seq(EnglishSquashScoring, PointARallySquashScoring, AnySquashScoring)
}
