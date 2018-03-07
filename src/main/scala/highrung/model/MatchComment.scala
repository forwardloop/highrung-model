package highrung.model

import org.joda.time.DateTime

case class MatchComment(
  id: Option[Int],
  matchId: Int,
  playerId: Int,
  text: String,
  createdTs: DateTime)