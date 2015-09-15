package demo.cleanpaths

import spray.routing.HttpService

trait JunkRoute extends HttpService {
    val routes = path("junk" / "mine") {
        get {
            complete("MINE!")
        }
    } ~ path("junk" / "yours") {
        get {
            complete("YOURS!")
        }
    }
}
