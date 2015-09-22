package demo.rejections

import demo.Stuff
import demo.Stuff._
import spray.http.MediaTypes
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import spray.routing.HttpService

trait StuffRoute extends HttpService {
    implicit def executionContext = actorRefFactory.dispatcher


    val stuffMap = Map(1 -> Stuff(1, "my stuff"), 2 -> Stuff(2, "your stuff"))
    val routes = {
        respondWithMediaType(MediaTypes.`application/json`) {
            path("stuff" / IntNumber) { (id) =>
                get {
                    stuffMap.get(id) match {
                        case Some(stuff) => complete(stuff)
                        case None => complete(NotFound, s"No stuff with id $id was found!")
                    }
                }
            } ~
              path("stuff") {
                  post {
                      entity(as[Stuff]) { stuff =>
                          if (stuff.id <= 0) throw new IllegalArgumentException("Id cannot be less than 1!")
                          complete(Stuff(stuff.id + 100, stuff.data + " posted"))
                      }
                  }
              }
        }
    }
}
