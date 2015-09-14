package demo.organizing

import spray.routing.HttpService

trait JunkRoute extends HttpService {
    val routes = pathPrefix("junk") {
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
//    ~ get {
//        complete("root from junk")
//    }
}
