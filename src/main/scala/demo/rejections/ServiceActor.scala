package demo.rejections

import spray.http.StatusCodes._
import spray.routing.{ExceptionHandler, HttpServiceActor}

class ServiceActor extends HttpServiceActor {
    override def actorRefFactory = context

    implicit def exceptionHandler = ExceptionHandler {
        case ex: IllegalArgumentException => complete(BadRequest, ex.getMessage)
    }

    val stuff = new StuffRoute {
        override implicit def actorRefFactory = context
    }

    val junk = new JunkRoute {
        override implicit def actorRefFactory = context
    }

    def receive = runRoute(junk.routes ~ stuff.routes)

}
