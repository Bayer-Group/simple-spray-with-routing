package demo.original

import akka.actor.Actor
import spray.routing.HttpService

import scala.concurrent._

class SampleServiceActor extends Actor with SampleRoute {
    def actorRefFactory = context

    def receive = runRoute(routes)
}

trait SampleRoute extends HttpService {
    import spray.http.MediaTypes
    implicit def executionContext: ExecutionContextExecutor = actorRefFactory.dispatcher

    val routes = {
        path("stuff") {
            respondWithMediaType(MediaTypes.`application/json`) {
                get {
                    complete(Stuff(1, "my stuff"))
                } ~
                  post {
                      entity(as[Stuff]) { stuff =>
                          complete(Stuff(stuff.id + 100, stuff.data + " posted"))
                      }
                  }
            }
        } ~
        pathPrefix("junk") {
            pathPrefix("mine") {
                pathEnd {
                    get {
                        complete("MINE!")
                    }
                }
            } ~ pathPrefix("yours") {
                pathEnd {
                    get {
                        complete("YOURS!")
                    }
                }
            }
        }
    }
}

