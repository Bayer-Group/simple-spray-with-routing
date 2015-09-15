package demo


import akka.actor.{ActorSystem, Props}
import akka.io.IO
import demo.cleanpaths.ServiceActor
import spray.can.Http

object Main extends App {
    implicit val system = ActorSystem("service")

    val service = system.actorOf(Props[ServiceActor], "service")

    //If we're on cloud foundry, get's the host/port from the env vars
    lazy val host = Option(System.getenv("VCAP_APP_HOST")).getOrElse("localhost")
    lazy val port = Option(System.getenv("VCAP_APP_PORT")).getOrElse("8081").toInt
    IO(Http) ! Http.Bind(service, host, port = port)

}
