package o1.appropeli


// Action -luokka edustaa toimintoja, joita pelihahmo voi suorittaa Appropelin aikana

class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

 // Saa pelihahmon suorittamaan käsketyn toimenpiteen.
 // Palauttaa toiminnan tuloksen Option-muodossa.
  def execute(actor: Player) = {                             
  def autoCorrect(sana: String, toinen: String) = o1.util.editDistance(sana, toinen, 1) <= 1
    
  if (autoCorrect(this.verb,"mene")) {
      Some(actor.mene(this.modifiers))
    } else if (autoCorrect(this.verb,"luovutan")) {
      Some(actor.luovutan())
    } else if (autoCorrect(this.verb,"katso")) {
      Some(actor.katso(this.modifiers))
    } else if (autoCorrect(this.verb,"puhu")) {
      Some(actor.puhu(this.modifiers))
    } else if (autoCorrect(this.verb,"valitsen")) {
      Some(actor.valitsen(this.modifiers))
    } else if (autoCorrect(this.verb,"tilaa")) {
      Some(actor.tilaa(this.modifiers))
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

}

