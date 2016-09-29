package actors

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, Props}
import akka.actor.Actor.Receive

case class ProcessNumber(num: Int)

object ProcessCounter {
  val props = Props[ProcessCounter]
}

class ProcessCounter extends Actor{
  override def receive: Receive = {
    case ProcessNumber(num) =>
      println(s"Processing $num")
      TimeUnit.SECONDS.sleep(1)
  }
}
