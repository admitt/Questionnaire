package ee.yhik

import collection.mutable

object Sessions {

  private val sessions = mutable.Map[Long, QuestionBankSession]()

  def put(session: QuestionBankSession) {
    sessions.put(session.id, session)
  }

  def drop(session: QuestionBankSession) {
    sessions.remove(session.id)
  }

  def get(id: Long) = {
    sessions.get(id)
  }
}
