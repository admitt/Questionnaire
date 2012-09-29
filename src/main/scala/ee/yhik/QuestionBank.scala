package ee.yhik

import io.Source
import ee.yhik.QuestionBank.Question
import util.Random
import scala.None

object QuestionBank {
  lazy val questions = createQuestions()
  val Question = """(\d+);(.+);(.+);(.+);(.+);(.+);(\d)""".r

  def apply() = {
    new QuestionBankSession(Random.shuffle(questions))
  }

  def createQuestions() = {
    Source.fromFile("questions.csv").getLines().map {
      _ match {
        case Question(id, q, a1, a2, a3, a4, right) => new Question(id.toInt, q, a1, a2, a3, a4, right.toInt)
        case line => throw new IllegalArgumentException("Data file is broken. Line=" + line)
      }
    }.toList
  }

  class Question(id: Int, question: String,
                 answer1: String, answer2: String,
                 answer3: String, answer4: String,
                 rightAnswer: Int) {
    override def toString = {
      new StringBuilder("Question#").append(id).append("\n")
        .append(question).append("\n")
        .append("1)").append(answer1).append("\n")
        .append("2)").append(answer2).append("\n")
        .append("3)").append(answer3).append("\n")
        .append("4)").append(answer4).toString()
    }
  }
}

class QuestionBankSession(var questions: List[Question]) {
  def nextQuestion() = {
    questions match {
      case head :: tail => {
        questions = tail
        Some(head)
      }
      case _ => None
    }
  }
}
