package ee.yhik

import ee.yhik.QuestionBank._

class QuestionBankSessionTest extends org.scalatest.FunSuite {

  val questions = List(
    new Question(1, "question", "answer1", "answer2", "answer3", "answer4", 2),
    new Question(2, "question", "answer1", "answer2", "answer3", "answer4", 3),
    new Question(3, "question", "answer1", "answer2", "answer3", "answer4", 1)
  )

  test("Get questions") {
    val session = new QuestionBankSession(1, questions)

    assert(session.nextQuestion().get eq questions(0))
    assert(session.nextQuestion().get eq questions(1))
    assert(session.nextQuestion().get eq questions(2))
    assert(session.nextQuestion() eq None)
  }

}
