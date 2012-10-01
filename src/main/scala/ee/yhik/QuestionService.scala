package ee.yhik

import org.scalatra._
import scalate.ScalateSupport

class QuestionService extends ScalatraServlet with ScalateSupport {

  get("/") {
    val session = QuestionBank()
    Sessions.put(session)
    redirect("/session/" + session.id)
  }

  get("/session/:id") {
    withSession {
      nextAction(_)
    }
  }

  post("/session/:id") {
    withSession {
      session => {
        session.addAnswer(params("questionId").toLong, params("answer").toInt)
        nextAction(session)
      }
    }
  }

  def nextAction(session: QuestionBankSession) = {
    session.nextQuestion() match {
      case Some(question) => {
        contentType = "text/html"

        scaml("/views/question.scaml",
          "question" -> question)
      }
      case None => {
        Sessions.drop(session)
        "Finished! Result " + session.rightAnswers + "/" + session.totalQuestions
      }
    }
  }

  def withSession(f: QuestionBankSession => String) = {

    Sessions.get(params("id").toLong) match {
      case Some(session) => f(session)
      case None => resourceNotFound()
    }
  }

  notFound {
    // remove content type in case it was set through an action
    contentType = null
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map { path =>
      contentType = "text/html"
      layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }
}
