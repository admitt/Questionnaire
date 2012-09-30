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
    Sessions.get(params("id").toLong) match {
      case Some(session: QuestionBankSession) => {
        session.nextQuestion() match {
          case Some(question) => {
            contentType = "text/html"
            scaml("/views/question.scaml",
              "question" -> question)
          }
          case None => "Finished!"
        }
      }
      case None =>  halt(404, "Not found!")
    }
  }

  post("session/:id") {

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
