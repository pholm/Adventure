package o1.appropeli

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer
import scala.io.Source

  
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
 //Näit parii vihreet EI Salee tarvitakkaa mut en kumita viel  private var ryhmanVikaArvo = new Group("Väärin kirjoitettu", Vector[Person](),0,0,0)
  private var porukat = Map[String, Group]()  //.withDefaultValue(ryhmanVikaArvo)
//  private var henkilonVikaArvo = new Person("väärinkirjoitettu",this.location,"antti is rontti")
  private var henkilot = Map[String,Person]()
  private var haasteheitetty = false
  private var vaarinmennyt = 0
  private var maxVaara = 5
  private var piilosana  = Vector[String] ("___", "___", "___" , "___",  "___", "___", "___", "___", "___")
  private var tavoitesana= Vector[String] (" H ", " U ", " M " , " A ",  " N ", " I ", " S ", " T ", " I ")
  
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
  def addHenkiloita(henkilot: Vector[(String,Person)]) = this.henkilot ++= henkilot
  
  def valitsen(ryhmanNimi: String) = {
    if(!this.ryhmä.isDefined) {
      if(porukat.contains(ryhmanNimi)) {
        this.ryhmä = Some(porukat(ryhmanNimi))
        this.vaadittukanni = porukat(ryhmanNimi).juomaMaara
        this.maximiaika = porukat(ryhmanNimi).aikaMaara
        this.rahat = porukat(ryhmanNimi).rahaMaara
         var ryhmanNimet = Buffer[String]()
        for(tyypit <- ryhmä.get.jasenet) { ryhmanNimet += tyypit.name }
        addHenkiloita(ryhmanNimet.toVector.zip(porukat(ryhmanNimi).jasenet)) //.withDefaultValue(henkilonVikaArvo)
        this.currentLocation = this.location.neighbor("kampin alakertaan").get
        "Valitsit " + ryhmanNimi.capitalize + "!" 
      } else "Valitettavasti et ole käynyt tarpeeksi pöhisemässä verkostoitumistapahtumissa, joten nämä ovat ainoat mahdollisuutesi. Valitse tietenkin (?) Tutalaiset, jos et osaa päättää!"
    } else "Tiedetään, ryhmävalintasi ei ollut nappiosuma, mutta olisi todella epäkohteliasta vaihtaa porukkaa kesken appron."
  }
  
 def puhu(personName: String) = {
  if(henkilot.contains(personName)) {
     if(henkilot(personName).sijainti == this.location) {
       if(personName == "mysteerimies") {
       haasteheitetty = true
       henkilot(personName).tunnuslause
       }
       henkilot(personName).tunnuslause
     } else "Ei näy kaveria missään, ehkä hän on seuraavassa kapakassa!"     
   } else "Ei ole kyllä tuommoisesta kaverista kuullut edes herra Peter 'network' Kelly."  
 }

  def kyllä () = {
    "Mainiota, rakastan ihmisiä jotka tykkäävät pelata.\nArvaa kirjain kerrallaan mitä kahdeksankirjaimista sanaa ajattelen." + 
    "\nSinulla on 4 väärää vastausta käytettävänäsi, voittaja tarjoaa häviäjälle oluen!\n" + 
    piilosana.mkString(" ") +
    "\n(arvaa sanomalla 'arvaan [kirjain]'"    
  }
 
  def ei () = {
    haasteheitetty = false
    "Ei sitten, senkin mammanpoika!"
  }
  
  private def paljasta(paljastettavat: Vector[String]) = {
    var uusiPiilosana = piilosana.toBuffer                             
    for (indeksi <- this.piilosana.indices) {
      if (paljastettavat(indeksi) != "___") {
        uusiPiilosana(indeksi) = paljastettavat(indeksi)    
      } else {
        uusiPiilosana(indeksi)  = piilosana(indeksi)
      } 
    }
    uusiPiilosana.toVector
  }
  
  def arvaan(arvaus: String) = {
    if(haasteheitetty == true) {
    val arvausIsona = arvaus.toUpperCase.toCharArray.head
    var kikka = Buffer[String]()    
      for(kirjaimet <- "HUMANISTI") {
       if(kirjaimet != arvausIsona) {
         kikka += "___" 
       } else kikka += " "+ arvausIsona +" "       
    }
    if(paljasta(kikka.toVector) == this.piilosana) {
      vaarinmennyt += 1
    if(vaarinmennyt < maxVaara) {
      piilosana.mkString(" ") +
      "\n\nAijai, väärin meni. Olet " + (maxVaara-vaarinmennyt) +" virheen pääse tappiosta!"
    } else {
      haasteheitetty = false
      rahat -= 5
      this.piilosana = Vector[String] ("___", "___", "___" , "___",  "___", "___", "___", "___", "___")
      "Kjehkjeh! Hyvä yritys! Olutrahaa pöytään juniori! \n(Rahavarasi pienenivät oluen hinnalla)" 
    } 
      
    } else {
      if(paljasta(kikka.toVector) != this.tavoitesana) {
        this.piilosana = paljasta(kikka.toVector)
        piilosana.mkString(" ") +"\n\nOho! Lähenee jo"      
      } else {
        this.piilosana = Vector[String] ("___", "___", "___" , "___",  "___", "___", "___", "___", "___")
        this.juomamaara += 1
        this.kusi += 1
        haasteheitetty = false
        tavoitesana.mkString(" ") + "\n\nOhhoh, en olisi uskonut, että kykenet tämän ratkaisemaan! Olet olueis ansainnut!" +
        "\n(Voitit mysteerimiehen haasteen ja sait ylimääräisen oluen!)"
      }} 
   
  }else "Mitä se siinä yksikseen arvailee."
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
    if(haasteheitetty == false){
    if(this.ryhmä.isDefined) {
    val nykysijainti = this.currentLocation
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation) 
    if (destination.isDefined) {
      this.kusi += 1 
      nykysijainti.onkoKayty = false
      this.ryhmä.foreach(_.jasenet.foreach(_.sijainti = destination.get))
      "Menit " + direction + "." 
    } else "Et voi mennä " + direction + "."
    } else "Yritit livahtaa kaveriesi ohi, mutta viime hetkellä aina kärppänä oleva Antti 'rontti' Ihalainen bongaa sinut. Joudut siis valitsemaan jonkun ryhmistä."
  } else "Älä yritä luikkia pakoon mysteerimiestä!\nVastaa pyyntöön kyllä tai ei."
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
  
  def help() = {
    
    val tiedosto = Source.fromFile("README.md")
    var tulos = ""

    try {
      var rivinumero = 1              // askeltaja
      for (rivi <- tiedosto.getLines) {
        tulos += rivi
        tulos += "\n"
        rivinumero += 1
      }
  
    } finally {
      tiedosto.close()
    }
    tulos
   }
  
  

  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name   
     

}






