package highrung.model

object MatchResult {

  val SingleRegex = """([0-9]+):([0-9]+)""".r // 9:1
  val DetailedRegex = """[0-9]+:[0-9]+ \(([[0-9]+:[0-9]+,?]+)\)""".r // 3:0 (9:1,9:8,9:5)
  val ForfeitRegex = """f([1-2])""".r // f1

  def apply(result: String): MatchResult =
    result match {
      case SingleRegex(p1Games, p2Games) => Overall(Score(p1Games.toInt, p2Games.toInt))
      case DetailedRegex(commaSepGames) =>
        val res = commaSepGames
          .split(',')
          .toList
          .foldLeft(List[Game]())((acc, game) =>
            game match {
              case SingleRegex(p1Points, p2Points) => Game(p1Points.toInt, p2Points.toInt) :: acc
              case _ => acc
            })
        Detailed(res.reverse)
      case ForfeitRegex(f) => Forfeit(f.toInt)
      case _ => Undefined
    }

  def apply(
    games: List[Game] = Nil,
    score: Option[Score] = None,
    forfeit: Option[Int] = None): MatchResult =
    (games, score, forfeit) match {
      case (game :: tail, None, None) => Detailed(game :: tail)
      case (Nil, Some(score), None) => Overall(score)
      case (Nil, None, Some(playerId)) => Forfeit(playerId)
      case _ => Undefined
    }

  def unapply(result: MatchResult): Option[String] = result match {
    case Forfeit(pId) => Some(s"f$pId")
    case Overall(score) => Some(s"${score.gamesP1}:${score.gamesP2}")
    case Detailed(games) =>
      val buf = games.map(g => s"${g.points1}:${g.points2}")
      val detailed = s" (" + (buf mkString (",")) + ")"
      val wonP1 = games.filter(_.player2Lost).size
      val wonP2 = games.filter(_.player1Lost).size
      val cumulative = s"$wonP1:$wonP2"
      Some(cumulative + detailed)
    case Undefined => None
  }
}

sealed trait MatchResult
case class Overall(score: Score) extends MatchResult
case class Detailed(games: List[Game]) extends MatchResult
case class Forfeit(benefitingPlayerId: Int) extends MatchResult
case object Undefined extends MatchResult
