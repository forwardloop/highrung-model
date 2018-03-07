package highrung.model

import org.joda.time.DateTime

case class Message(
  id: Option[Int] = None,
  parentId: Int,
  tournamentId: Int,
  subject: String,
  text: String,
  updatedBy: Int,
  updateTs: DateTime = DateTime.now(),
  deleted: String = "N")