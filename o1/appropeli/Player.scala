package o1.appropeli

import scala.collection.mutable.Map

  
/** A `Player` object represents a player character controlled by the real-life user of the program. 
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  *
  * @param startingArea  the initial location of the player */
class Player(startingArea: Area) {

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private val itemList = Map[String, Item]()
  private var kusi = 0
  private var juomamaara = 0
  var porukka = 0
  
  def vessahata = this.kusi
  def juodut = this.juomamaara
  def drop(itemName: String) = {
    if(has(itemName)) {
    this.currentLocation.addItem(this.itemList(itemName))
    itemList.remove(itemName)
    s"You drop the $itemName."
    } else "You don't have that!"
  }
  def examine(itemName: String) = {
    if(!has(itemName)) "If you want to examine something, you need to pick it up first."
    else s"You look closely at the $itemName.\n" + itemList(itemName).description
  }
  
  def has(itemName: String) = itemList.contains(itemName)
  
  def inventory = {
    if(itemList.isEmpty) "You are empty-handed."
    else "You are carrying:\n" + itemList.keys.mkString("\n")
    
  }
  
  def get(itemName: String) = {
    if(this.currentLocation.contains(itemName)) {
      this.itemList += itemName -> this.currentLocation.removeItem(itemName).get
      s"You pick up the $itemName."   
  } else s"There is no $itemName here to pick up." 
  }

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  
  /** Returns the current location of the player. */
  def location = this.currentLocation
  

  /** Attempts to move the player in the given direction. This is successful if there 
    * is an exit from the player's current location towards the direction name. 
    * Returns a description of the results of the attempt. */
  def mene (direction: String) = {
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation) 
    if (destination.isDefined) {
      this.kusi += 1 
      "Menit " + direction + "." 
    } else "Et voi mennä " + direction + "."
   
  }

  
  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() = {
    "You rest for a while. Better get a move on, though." 
  }
  
  
  /** Signals that the player wants to quit the game. Returns a description of what happened within 
    * the game as a result (which is the empty string, in this case). */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }

  def tilaa(juoma: Option[Drink]) = {
    this.juomamaara += 1
    if (juoma == this.location.removeDrink("Talon olut")) {
      "Tilasit oluen. Joit huurteisen yhdellä kulauksella!"
    } else if (juoma == this.location.removeDrink("Viina"))
      "Joit viinan ja irvistit kuin fuksi."
  }
  
  def talk(person: String) = {
    
  }
  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name   
     

}





