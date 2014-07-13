package actors.ws

import actors.ws.WSClientMsgs.JsToClient
import play.api.libs.iteratee._
import play.api.libs.json._
import play.api.mvc.RequestHeader

object SocketManager extends WebSocketManager[SocketManager] {
  case object AlertForSomething
  case class AlertOnlyMe(uuid: String)

}

class SocketManager extends WSManagerActor {

  import SocketManager._
  import WSClientMsgs._

  override def operative(implicit request: RequestHeader) = {
    (wsClient) => {
      case AlertForSomething =>
        wsClient ! JsToClient(Json.obj(
          "broadcast" -> "fromServer"
        ))
    }
  }
}