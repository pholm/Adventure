

/** PELI ON OPTIMOITU GUILLE, KÄYTÄ TEXTUITA VAIN ERITYISESTÄ HALUSTA! (kiitos!)


package o1.appropeli.ui

import o1.appropeli._
import scala.io.StdIn._

object AppropeliTextUI extends App {
  
  private val game = new Appropeli
  private val player = game.player
  this.run()

  
  /** Runs the game. First, a welcome message is printed, then the player gets the chance to 
    * play any number of turns until the game is over, and finally a goodbye message is printed. */
  private def run() = {
    println(this.game.welcomeMessage)
    while (!this.game.isOver) {
      this.printAreaInfo()
      this.playTurn()
    } 
    println("\n" + this.game.goodbyeMessage)
  }


  /** Prints out a description of the player character's current location, as seen by the character. */
  private def printAreaInfo() = {
    val area = this.player.location
    println("\n\n" + area.name)
    println("-" * area.name.length)
    println(area.fullDescription + "\n")
  }


  /** Requests a command from the player, plays a game turn accordingly, and prints out a report of what happened.  */
  private def playTurn() = {
    println()
    val command = readLine("Command: ")
    val turnReport = this.game.playTurn(command)
    if (!turnReport.isEmpty) {
      println(turnReport) 
    }
  }

}

**/
