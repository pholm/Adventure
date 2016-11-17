package o1.appropeli


/** The class Adventure represents text adventure games. An adventure consists of a player and 
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of "hard-coded" information which pertain to a very 
  * specific adventure game that involves a small trip through a twisted forest. All newly created 
  * instances of class Adventure are identical to each other. To create other kinds of adventure 
  * games, you will need to modify or replace the source code of this class. */
class Appropeli {

  /** The title of the adventure game. */
  val title = "Appropeli"
  // Luodaan alueet
  private val Kampin_alakerta     = new Area("Kampin alakerta", "Olet Kampin alakerrassa. Varo vartijoita pakoon juoksevia teinejä!")  
  private val Kampin_yläkerta     = new Area("Kampin yläkerta", "Olet Kampin yläkerrassa.") 
  private val Narinkkatori        = new Area("Narinkkatori", "Olet Narinkkatorilla.")
  private val Fredrikinkatu       = new Area("Fredrikinkatu", "Olet Fredrikinkadulla.")
  private val Tennispalatsinaukio = new Area("Tennispalatsinaukio", "Olet Tennispalatsinaukiolla.")
  private val Bruuveri            = new Area("Bruuveri", "Olet Bruuverissa.")
  private val AussieBar           = new Area("Aussie Bar", "Olet Aussie Barissa.")
  private val Bierhuis_Rotterdam  = new Area("Bierhuis Rotterdam", "Olet Bierhuis Rotterdamissa.")
  private val Pub_Ikkuna          = new Area("Pub Ikkuna", "Olet Pub Ikkunassa") 
  private val Henrys_pub          = new Area("Henry's Pub", "Olet Henry's Pubissa.")
  private val Shaker              = new Area("Shaker", "Olet Shakerissa.")
  private val WilliamK            = new Area("William K", "Olet William K:ssa.")
  private val Teerenpeli          = new Area("Teerenpeli", "Olet Teerenpelissä.")
  private val VessaKamppi         = new Area("Kampin vessa", "Olet Kampin vessassa.")
  private val VeskiHenry          = new Area("Henry's Pubin vessa", "Olet Henry's Pubin vessassa.")
  private val Circus              = new Area("Circus", "Olet Circuksessa.")
  private val alkupaikka           = new Area("Alkupaikka", "Tervetuloa pelaamaan Appropeliä. TÄHÄN PELIN SÄÄNNÖT")
  private val destination         = Circus    

  // Määritellään alueille naapurialueet 
  Kampin_alakerta.setNeighbors(Vector(    "kampin yläkertaan" -> Kampin_yläkerta, "vessaan" -> VessaKamppi))
  Kampin_yläkerta.setNeighbors(Vector(    "bruuveriin" -> Bruuveri, "bierhuis rotterdamiin" -> Bierhuis_Rotterdam, "narinkkatorille" -> Narinkkatori, "tennispalatsinaukiolle" -> Tennispalatsinaukio,   "kampin alakertaan" -> Kampin_alakerta))
  Narinkkatori.setNeighbors(Vector(       "pub ikkunaan" -> Pub_Ikkuna, "henry's pubiin" -> Henrys_pub, "tennispalatsinaukiolle" -> Tennispalatsinaukio, "kampin yläkertaan" -> Kampin_yläkerta, "circukseen" -> Circus  ))
  Tennispalatsinaukio.setNeighbors(Vector("teereenpeliin" -> Teerenpeli,      "aussie bariin" -> AussieBar, "fredrikinkadulle" -> Fredrikinkatu, "kampin yläkertaan" -> Kampin_yläkerta   ))
  Fredrikinkatu.setNeighbors(Vector(      "william koohon" -> WilliamK, "shakeriin" -> Shaker, "tennispalatsinaukiolle" -> Tennispalatsinaukio))
  Bruuveri.setNeighbors(Vector(           "kampin yläkertaan" -> Kampin_yläkerta))
  Bierhuis_Rotterdam.setNeighbors(Vector( "kampin yläkertaan" -> Kampin_yläkerta))
  Pub_Ikkuna.setNeighbors(Vector(         "narinkkatorille" -> Narinkkatori))
  Henrys_pub.setNeighbors(Vector(         "narinkkatorille" -> Narinkkatori, "veskiin" -> VeskiHenry))
  Shaker.setNeighbors(Vector(             "fredrikinkadulle" -> Fredrikinkatu))
  WilliamK.setNeighbors(Vector(           "fredrikinkadulle" -> Fredrikinkatu))
  AussieBar.setNeighbors(Vector(          "tennispalatsinaukiolle" -> Tennispalatsinaukio))
  Teerenpeli.setNeighbors(Vector(        "tennispalatsinaukiolle" -> Tennispalatsinaukio))
  VessaKamppi.setNeighbors(Vector(        "kampin alakertaan" -> Kampin_alakerta, "vessanpönttöön" -> VeskiHenry))
  VeskiHenry.setNeighbors(Vector(         "henry's pubiin" -> Henrys_pub, "vessanpönttöön" -> VessaKamppi))
  Circus.setNeighbors(Vector(             "narinkkatorille" -> Narinkkatori))
  alkupaikka.setNeighbors(Vector(  "kampin alakertaan" -> Kampin_alakerta))
  
  /* Luodaan pelihahmo ja asetetaan se "alkupaikkaan". Alkupaikassa pelaaja valitsee ryhmän, jonka jälkeen hahmo siirtyy Kampin alakertaan
   ja peli alkaa */
  val player = new Player(alkupaikka)
  
  // luodaan muuttuja a, jotta sitä voidaan käyttää sijaintina ryhmien jäseniä luotaessa
  val a = this.Kampin_alakerta
  
  // luodaan peliin henkilöitä. Ryhmiin kuuluvilla henkilöillä alkusijainti on sama kuin pelaajalla. 
  private val rontti = new Person("rontti",a,"olen nousu_humalassa")
  private val akseli = new Person("akseli",a,"vitunkymisvittu")
  private val kymis = new Person("kymis",a,"en ainakaan nouse ennen kahtatoista")
  private val janpaul = new Person("jan-paul",a,"Lähtisitkö viikonloppuna purjehtimaan Dragsvikiin?") 
  private val christoffer = new Person("christoffer",a,"Tänään otetaan!")
  private val erik = new Person("erik",a,"Mä oon tänään vesilinjalla, älä syyyllistä.")
  private val henrik = new Person("henrik",a,"Mun rahat on loppu.")
  private val ville = new Person("ville",a,"Mun tekstipeli on parempi ku sun.")
  private val tiina = new Person("tiina",a,"Info on paras")
  //luodaan ryhmiin kuulumattomat henkilöt
  private val spusse = new Person("spusse",Henrys_pub,"Juodaan viinaa, tullaan viisaammiksi näin!")
  private val puustinen = new Person("puustinen",Bruuveri,"Minähän juon tunnetusti vain pienpanimoiden tuotteita.")
  // lisätään henkilöt henkilöhakemistoon
  player.addHenkiloita(Vector(spusse.name -> spusse, puustinen.name -> puustinen))
  // luodaan ryhmät, joissa jokaisessa on 3 jäsentä
  val group1 = new Group("paatuneet tutalaiset", Vector[Person](rontti,akseli,kymis),5,30,1000)
  val group2 = new Group("syntiset kylterit",Vector[Person](janpaul,christoffer,erik),10,20,100)
  val group3 = new Group("viattomat infolaiset",Vector[Person](henrik,ville,tiina),2,50,200)
  
  //lisätään edellä luodut ryhmät pelaajan valittavissa oleviin ryhmiin
  this.player.addGroups(Vector(group1.nimi -> group1, group2.nimi -> group2, group3.nimi -> group3))
  
  // lista kaikista baareista
  private val baarit = Vector[Area](Bruuveri, AussieBar, Bierhuis_Rotterdam, Pub_Ikkuna, Henrys_pub, Shaker, WilliamK, Teerenpeli)
  
  //lisätään jokaisen baarin tarjontaan olut ja viina
  baarit.foreach(_.addDrink(new Drink("talon olut","Mainiota Olvi kolmosta opiskelijahintaan","Tilasit oluen. Joit huurteisen yhdellä kulauksella!")))
  baarit.foreach(_.addDrink(new Drink("viina","Räväkkä Ko-ko-ko-koskeeen ko-ko-ko-korvaaaa tyydyttää janoisemmankin teekkarin","Joit viinan ja irvistit kuin fuksi.")))
  


  var juodut = player.juodut
  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40 


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = this.player.location == this.destination && this.player.has("remote") && this.player.has("battery")

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */ 
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit || this.player.vessahata >= 10

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = {
    "Koodasit viime yönä aivan liian pitkään, joten nukuit tänään pommiin ja saavut nyt myöhässä bussilla Kampin terminaaliin, jossa tapaat kolme eri kavereistasi koostuvaa porukkaa.\n" +  
    "Minkä porukan mukaan haluat lähteä? Valitse kirjoittamalla 'valitsen [porukan nimi]'.\n" +
    "Vaihtoehtoina ovat Viattomat infolaiset, Syntiset kylterit sekä Paatuneet tutalaiset."
    }

    
  /** Returns a message that is to be displayed to the player at the end of the game. The message 
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
    if (this.isComplete)
      s"Tervetuloa jatkoille! Keräsit $juodut leimaa, joten saat haalarimerkin!"
    else if (this.turnCount == this.timeLimit)
      "Et ehtinyt jatkoille asti ajoissa!"
    else if (this.player.vessahata >= 10)
      "Pissasit housuun! Koko Helsinki nauraa sinulle ja joudut poistumaan häpeissän takaisin Otaniemen suojiin."
    else  // game over due to player quitting
      "Quitter!" 
  }

  
  /** Plays a turn by executing the given in-game command, such as "go west". Returns a textual 
    * report of what happened, or an error message if the command was unknown. In the latter 
    * case, no turns elapse. */
  def playTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    if (outcomeReport.isDefined) { 
      this.turnCount += 1 
    }
    outcomeReport.getOrElse("Olet jo niin humalassa, ettei puheestasi saa selvää. \nSanoppa uudestaan.")
  }
  
  
}