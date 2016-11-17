package o1.appropeli

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer

  
/** A `Player` object represents a player character controlled by the real-life user of the program. 
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  *
  * @param startingArea  the initial location of the player */
class Player(startingArea: Area) {

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private val itemList = Map[String, Item]()
  private var ryhmä: Option[Group] = None
  private var kusi = 0
  private var juomamaara = 0
  private var vaadittukanni = 0
  private var maximiaika = 0
  private var rahat = 0
  private var ryhmanVikaArvo = new Group("Väärin kirjoitettu", Vector[Person](),0,0,0)
  private var porukat = Map[String, Group]().withDefaultValue(ryhmanVikaArvo)
  private var henkilonVikaArvo = new Person("väärinkirjoitettu",this.location,"antti is rontti")
  private var henkilot = Map[String,Person]()
  
  def vessahata = this.kusi
  def juodut = this.juomamaara
  def drop(itemName: String) = {
    if(has(itemName)) {
    this.currentLocation.addItem(this.itemList(itemName))
    itemList.remove(itemName)
    s"You drop the $itemName."
    } else "You don't have that!"
  }
 
  
  def addGroups(ryhmat: Vector[(String, Group)]) = this.porukat ++= ryhmat
      
  def valitsen(ryhmanNimi: String) = {
    if(!this.ryhmä.isDefined) {
      if(porukat(ryhmanNimi) != ryhmanVikaArvo) {
        this.ryhmä = Some(porukat(ryhmanNimi))
        this.vaadittukanni = porukat(ryhmanNimi).juomaMaara
        this.maximiaika = porukat(ryhmanNimi).aikaMaara
        this.rahat = porukat(ryhmanNimi).rahaMaara
        
        var ryhmanNimet = Buffer[String]()
        for(tyypit <- ryhmä.get.jasenet) { ryhmanNimet += tyypit.name }
        henkilot = (collection.mutable.Map() ++ (ryhmanNimet.toVector.zip(porukat(ryhmanNimi).jasenet))).withDefaultValue(henkilonVikaArvo)
        this.currentLocation = this.location.neighbor("kampin alakertaan").get
        "Valitsit " + ryhmanNimi.capitalize + "!" 
      } else "Valitettavasti et ole käynyt tarpeeksi pöhisemässä verkostoitumistapahtumissa, joten nämä ovat ainoat mahdollisuutesi. Valitse tietenkin (?) Tutalaiset, jos et osaa päättää!"
    } else "Tiedetään, ryhmävalintasi ei ollut nappiosuma, mutta olisi todella epäkohteliasta vaihtaa porukkaa kesken appron."
  }
  
 def puhu(personName: String) = {
   if(henkilot(personName) != henkilonVikaArvo) {
     if(henkilot(personName).sijainti == this.location) {
       henkilot(personName).tunnuslause
     } else "Ei näy kaveria missään, ehkä hän on seuraavassa kapakassa!"     
   } else "Ei ole kyllä tuommoisesta kaverista kuullut edes herra Peter 'network' Kelly."
   
   
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
    if(this.ryhmä.isDefined) {
    val nykysijainti = this.currentLocation
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation) 
    if (destination.isDefined) {
      this.kusi += 1 
      nykysijainti.onkoKayty = false
      this.ryhmä.foreach(_.jasenet.forall(_.sijainti == destination))
      "Menit " + direction + "." 
    } else "Et voi mennä " + direction + "."
    } else "Yritit livahtaa kaveriesi ohi, mutta viime hetkellä aina kärppänä oleva Antti 'rontti' Ihalainen bongaa sinut. Joudut siis valitsemaan jonkun ryhmistä."
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

  def tilaa(juoma: String) = {
    if (location.onkoKayty == false) {
      if (this.location.giveDrink(juoma) != this.location.giveDrink("tyhja")) {                        // Kaikki listan määritellyt juomat
        this.juomamaara += 1
        this.kusi += 2
        location.onkoKayty = true
        this.location.giveDrink(juoma).tilaamisenJalkeen      
      } else this.location.giveDrink(juoma).tilaamisenJalkeen
    } else "Kaveri hei, sun takana on kuuskymmentä janoista teekkaria, jatka matkaa jo!"
  }
  

  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name   
     

}





