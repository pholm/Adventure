package o1.appropeli

import scala.collection.mutable.Map

/** The class `Area` represents locations in a text adventure game world. A game world 
  * consists of areas. In general, an "area" can be pretty much anything: a room, a building, 
  * an acre of forest, or something completely different. What different areas have in 
  * common is that players can be located in them and that they can have exits leading to 
  * other, neighboring areas. An area also has a name and a description. 
  * @param name         the name of the area 
  * @param description  a basic description of the area (typically not including information about items) */
class Area(var name: String, var description: String) {
  
  private val neighbors = Map[String, Area]()
  private val items = Map[String, Item]()
  private val drinks = Map[String, Drink]().withDefaultValue(new Drink("Ei olla","Juomaasi ei ole listassa. Tilaa vikka viina, jos et muuta keksi.","Osta Bisse", 0))
  private val henkilot = Map[String, Person]()
  
  var onkoKayty = false
  def addItem(item: Item) = this.items += item.name -> item
  def contains(itemName: String) = items.contains(itemName)
  def removeItem(itemName: String) = items.remove(itemName)
  
  def lisaaHenkilo(henkilo: Person) = this.henkilot += henkilo.name -> henkilo
  
  def addDrink(drink: Drink) = this.drinks += drink.name -> drink
  def containsDrink(drinkName: String) = drinks.contains(drinkName)
  def giveDrink(drinkName: String) = drinks(drinkName)
  
  /** Returns the area that can be reached from this area by moving in the given direction. The result 
    * is returned in an `Option`; `None` is returned if there is no exit in the given direction. */
  def neighbor(direction: String) = this.neighbors.get(direction)

  
  /** Adds an exit from this area to the given area. The neighboring area is reached by moving in 
    * the specified direction from this area. */
  def setNeighbor(direction: String, neighbor: Area) = {
    this.neighbors += direction -> neighbor
  }

  
  /** Adds exits from this area to the given areas. Calling this method is equivalent to calling 
    * the `setNeighbor` method on each of the given direction--area pairs.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    * @see [[setNeighbor]] */
  def setNeighbors(exits: Vector[(String, Area)]) = {
    this.neighbors ++= exits
  }
  
  
  /** Returns a multi-line description of the area as a player sees it. This includes a basic 
    * description of the area as well as information about exits and items. */
  def fullDescription = {
    var exitit = this.neighbors.keys.map(_.capitalize).toVector
    val exitList = if(this.name !="Alkupaikka") {
      "\n\nKatsot ympärillesi, huomaat, että voitte mennä " + {if(exitit.size > 1) (exitit.init.mkString(", ") + " tai ") + exitit.last else "ainoastaan takaisin " + exitit.last}
    } else ""
    var kuvaus = this.description
    if(!this.items.isEmpty) {
    val esineet = "\nYou see here: " + this.items.keys.mkString(" ")
    kuvaus += esineet
    } 
    if(!this.henkilot.isEmpty) {
    val ihmiset = "\n\nHuomaat paikalla seuraavat ihmiset: " + this.henkilot.keys.mkString(" ").capitalize
    kuvaus += ihmiset
    } 
    if(!this.drinks.isEmpty) {
    val drinkit = "\n\nTalon drinkkilista tarjoaa seuraavaa: " + this.drinks.keys.mkString(", ")
    kuvaus += drinkit
    }  
    kuvaus + exitList
  } 
  
  
  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)

  
  
}
