package o1.appropeli


/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect, 
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as "go east" or "rest" */
class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  
  /** Causes the given player to take the action represented by this object, assuming 
    * that the command was understood. Returns a description of what happened as a result 
    * of the action (such as "You go west."). The description is returned in an `Option` 
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player) = {                             
  def autoCorrect(sana: String, toinen: String) = o1.util.editDistance(sana, toinen, 1) <= 1
    
  if (autoCorrect(this.verb,"mene")) {
      Some(actor.mene(this.modifiers))
    } else if (autoCorrect(this.verb,"rest")) {
      Some(actor.rest())
    }else if (autoCorrect(this.verb,"quit")) {
      Some(actor.quit())
    } else if (autoCorrect(this.verb,"katso")) {
      Some(actor.katso(this.modifiers))
    } else if (autoCorrect(this.verb,"inventory")) {
     Some(actor.inventory)
    } else if (autoCorrect(this.verb,"drop")) {
      Some(actor.drop(this.modifiers))
    } else if (autoCorrect(this.verb,"puhu")) {
      Some(actor.puhu(this.modifiers))
    } else if (autoCorrect(this.verb,"valitsen")) {
      Some(actor.valitsen(this.modifiers))
    } else if (autoCorrect(this.verb,"examine")) {
      Some(actor.examine(this.modifiers))
    }else if (autoCorrect(this.verb,"tilaa")) {
      Some(actor.tilaa(this.modifiers))
    } else if (autoCorrect(this.verb,"get")){
      Some(actor.get(this.modifiers))
    } else if (autoCorrect(this.verb,"kyllä")) {
      Some(actor.kyllä())
    } else if (autoCorrect(this.verb,"arvaan")) {
      Some(actor.arvaan(this.modifiers))
    } else if (autoCorrect(this.verb,"ei")) {
      Some(actor.ei())
    } else if (autoCorrect(this.verb,"help")) {
      Some(actor.help())
    } else {
      None
    }
    
  }


  
  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"  

  
}

