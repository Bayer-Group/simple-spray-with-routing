package demo.cleanpaths

import demo.Stuff
import demo.Stuff._
import spray.http.MediaTypes
import spray.httpx.SprayJsonSupport._
import spray.routing.HttpService

trait StuffRoute extends HttpService {
    implicit def executionContext = actorRefFactory.dispatcher

    val stuffMap = Map(1 -> Stuff(1, "my stuff"), 2 -> Stuff(2, "your stuff"))
    val routes = {
        respondWithMediaType(MediaTypes.`application/json`) {
            path("stuff" / IntNumber) { (id) =>
                get {
                    complete(stuffMap(id))
                }
            } ~
              path("stuff") {
                  post {
                      entity(as[Stuff]) { stuff =>
                          complete(Stuff(stuff.id + 100, stuff.data + " posted"))
                      }
                  }
              }
        }
    }
}
