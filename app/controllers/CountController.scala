package controllers

import javax.inject._

import actors.{ProcessNumber, ProcessCounter}
import akka.actor.{ActorRef, ActorSystem}
import play.api.mvc._
import services.Counter

/**
  * This controller demonstrates how to use dependency injection to
  * bind a component into a controller class. The class creates an
  * `Action` that shows an incrementing count to users. The [[Counter]]
  * object is injected by the Guice dependency injection system.
  */
@Singleton
class CountController @Inject()(counter: Counter, actorSystem: ActorSystem) extends Controller {

  /**
    * Create an action that responds with the [[Counter]]'s current
    * count. The result is plain text. This `Action` is mapped to
    * `GET /count` requests by an entry in the `routes` config file.
    */
  def count = Action {

    val nextCount: Int = counter.nextCount()

    val processCounterActor: ActorRef = actorSystem.actorOf(ProcessCounter.props)

    (1 to nextCount).foreach(num => {
      processCounterActor ! ProcessNumber(num)
    })

    Ok(nextCount.toString)
  }

}
