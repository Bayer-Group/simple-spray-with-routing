package demo.organizing

import demo.Stuff
import demo.Stuff._
import spray.http.MediaTypes
import spray.httpx.SprayJsonSupport._
import spray.routing.HttpService

trait StuffRoute extends HttpService {
    implicit def executionContext = actorRefFactory.dispatcher

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
        }
//        ~ get {
//            complete("root from stuff")
//        }
    }
}
