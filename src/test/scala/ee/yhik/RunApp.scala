package ee.yhik

import org.eclipse.jetty.webapp.WebAppContext

object RunApp {
  def main(args: Array[String]) {
    val server = new org.eclipse.jetty.server.Server(8080)

    server.setHandler(new WebAppContext("target/webapp", ""))

    server.start()
  }
}
