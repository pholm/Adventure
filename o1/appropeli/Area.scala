package o1.appropeli

import scala.collection.mutable.Map

// Area -luokka edustaa jokaista Appro: Option[humala] -pelin aluetta.
// Tämmöisiä ovat baarit ja alueet niiden välissä sekä baarien muutamat vessat


class Area(var name: String, var description: String, var musa: Option[String]) {
  
  private val neighbors = Map[String, Area]()            //alueen naapurit
  private val drinks = Map[String, Drink]().withDefaultValue(new Drink("Ei olla","Juomaasi ei ole listassa. Tilaa vikka viina, jos et muuta keksi.","Osta Bisse", 0)) //alueen drinkit (on jos alue on baari)
  private val henkilot = Map[String, Person]()          // henkilöt alueella
  var onkoKayty = false                                 // estää monien juomien tilaamisen putkeen
  
  def lisaaHenkilo(henkilo: Person) = this.henkilot += henkilo.name -> henkilo                          // lisää henkilön alueelle
  def addDrink(drink: Drink) = this.drinks += drink.name -> drink                                       // lisää juoman alueelle
  def addDrinks(drinkit: Vector[Drink]) = for(drink <- drinkit) {this.drinks += drink.name -> drink}    // lisää juomia alueelle
  def containsDrink(drinkName: String) = drinks.contains(drinkName)                                     // tutkii onko alueella annettu juoma
  def giveDrink(drinkName: String) = drinks(drinkName)                                                  // palauttaa annetun juoman
  
  // palauttaa alueen naapurin pyydetyssä suunnassa
  def neighbor(direction: String) = this.neighbors.get(direction)
  
  // asettaa alueelle naapureita
  def setNeighbors(exits: Vector[(String, Area)]) = {
    this.neighbors ++= exits
  }
  
  
  // palauttaa täydellisen kuvauksen alueesta
  def fullDescription = {
    var exitit = this.neighbors.keys.map(_.capitalize).toVector
    val exitList = if(this.name !="Alkupaikka") {
      "\n\nKatsot ympärillesi, huomaat, että voitte mennä " + {if(exitit.size > 1) (exitit.init.mkString(", ") + " tai ") + exitit.last else "ainoastaan takaisin " + exitit.last}
    } else ""
    var kuvaus = this.description    
    if(!this.henkilot.isEmpty) {
    val ihmiset = "\n\nHuomaat paikalla seuraavat ihmiset: " + this.henkilot.keys.mkString(", ").capitalize
    kuvaus += ihmiset
    } 
    if(!this.drinks.isEmpty) {
    val drinkit = "\n\nTalon drinkkilista tarjoaa seuraavaa: " + this.drinks.keys.mkString(", ")
    kuvaus += drinkit
    }  
    kuvaus + exitList
  } 
  

  
  
}
