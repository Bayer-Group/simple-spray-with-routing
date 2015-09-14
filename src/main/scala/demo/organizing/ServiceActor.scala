package demo.organizing

import spray.routing.HttpServiceActor

class ServiceActor extends HttpServiceActor {
    override def actorRefFactory = context

    val stuff = new StuffRoute {
        override implicit def actorRefFactory = context
    }

    val junk = new JunkRoute {
        override implicit def actorRefFactory = context
    }
    def receive = runRoute(junk.routes ~ stuff.routes)

}
