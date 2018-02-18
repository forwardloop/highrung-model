package highrung.model

object RacquetSport {

  final val SquashAbbrev = "squa"
  final val TennisAbbrev = "tenn"
  final val TableTennisAbbrev = "tete"
  final val RacquetballAbbrev = "raba"
  final val OtherAbbrev = "othe"

  def apply(abbreviation: String) = abbreviation match {
    case SquashAbbrev => Squash
    case TennisAbbrev => Tennis
    case TableTennisAbbrev => TableTennis
    case RacquetballAbbrev => Racquetball
    case _ => Other
  }
}

import RacquetSport._

sealed abstract class RacquetSport(val abbreviation: String)
case object Squash extends RacquetSport(SquashAbbrev)
case object Tennis extends RacquetSport(TennisAbbrev)
case object TableTennis extends RacquetSport(TableTennisAbbrev)
case object Racquetball extends RacquetSport(RacquetballAbbrev)
case object Other extends RacquetSport(OtherAbbrev)