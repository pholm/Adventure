package o1.appropeli
import o1.sound._




// kaduille ulkoilmanääni
// nokia tunnari, pera soittaa, äänitys
// joka baariin musa
// voittomusiikki
// tilaa metodin blokkaus jos ei baarissa

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
  private val Narinkkatori        = new Area("Narinkkatori", "Astuessasi Narinkkatorille viiltävä tuuli iskee kasvoillesi. Hankkiudu äkkiä takaisin sisätiloihin!")
  private val Fredrikinkatu       = new Area("Fredrikinkatu", "Olet Fredrikinkadulla.")
  private val Tennispalatsinaukio = new Area("Tennispalatsinaukio", "Olet Tennispalatsinaukiolla.")
  private val Bruuveri            = new Area("Bruuveri", "Tervetuloa Bruuveriin! Olemme tunnettuja laadukkaista, valtavirrasta erottuvista talon omista oluista ja siidereistä.")
  private val AussieBar           = new Area("Aussie Bar", "Welcome to Aussie Bar. You don't just visit Aussie Bar, you live it!")
  private val Bierhuis_Rotterdam  = new Area("Bierhuis Rotterdam", "Tervetuloa Bierhuis Rotterdamiin. Bierhuis on välitön hauskanpidon satama, jossa juttu lentää ja tuopit kolahtelevat.")
  private val Pub_Ikkuna          = new Area("Pub Ikkuna", "Tervetuloa Pub Ikkunaan. Pub Ikkuna on alkuillan starttipaikka Kampin keskuksessa.") 
  private val Henrys_pub          = new Area("Henry's Pub", "Tervetuloa Henry's Pubiin. Henry's Pub tarjoaa elävää musiikkia maanantaista torstaihin sekä tietenkin erinomaisia juomia moneen makuun.")
  private val Shaker              = new Area("Shaker", "Tervetuloa Shakeriin. Olemme tunnettu hyvistä cocktaileista, ammattitaitoisesta henkilökunnasta, hyvästä palvelusta ja rennosta ilmapiiristä.")
  private val WilliamK            = new Area("William K", "Kun haluat treffata ennen leffaa tai mennä fiilistelemään leffan jälkeen, William K. Fredrikinkatu Tennispalatsissa sopii tarkoitukseen kuin buutsit Chuck Norrisille.")
  private val Teerenpeli          = new Area("Teerenpeli", "Teerenpeli Panimo & Tislaamossa on näyttävällä ja tehokkaalla tavalla yhdistetty oluen valmistus ja tislaaminen.")
  private val VessaKamppi         = new Area("Kampin vessa", "Olet Kampin vessassa. Tänne ovat monien approt päättyneet ennenaikaisesti.")
  private val VeskiHenry          = new Area("Henry's Pubin vessa", "Olet Henry's Pubin vessassa.")
  private val Circus              = new Area("Circus", "Portsari puhalluttaa sinut eikä todellakaan päästä noin selvää kaveria tylsistyttämään tunnelmaa. \nMene käymään muutamassa kapakassa vielä!")
  private val alkupaikka          = new Area("Alkupaikka", "Tervetuloa pelaamaan Appropeliä.")
  val destination                 = Circus    

  
  // Määritellään alueille naapurialueet 
  Kampin_alakerta.setNeighbors(Vector(    "kampin yläkertaan" -> Kampin_yläkerta, "vessaan" -> VessaKamppi))
  Kampin_yläkerta.setNeighbors(Vector(    "bruuveriin" -> Bruuveri, "bierhuis rotterdamiin" -> Bierhuis_Rotterdam, "narinkkatorille" -> Narinkkatori, "tennispalatsinaukiolle" -> Tennispalatsinaukio,   "kampin alakertaan" -> Kampin_alakerta))
  Narinkkatori.setNeighbors(Vector(       "pub ikkunaan" -> Pub_Ikkuna, "henry's pubiin" -> Henrys_pub, "tennispalatsinaukiolle" -> Tennispalatsinaukio, "kampin yläkertaan" -> Kampin_yläkerta, "circukseen" -> Circus  ))
  Tennispalatsinaukio.setNeighbors(Vector("teerenpeliin" -> Teerenpeli,      "aussie bariin" -> AussieBar, "fredrikinkadulle" -> Fredrikinkatu, "kampin yläkertaan" -> Kampin_yläkerta   ))
  Fredrikinkatu.setNeighbors(Vector(      "william koohon" -> WilliamK, "shakeriin" -> Shaker, "tennispalatsinaukiolle" -> Tennispalatsinaukio))
  Bruuveri.setNeighbors(Vector(           "kampin yläkertaan" -> Kampin_yläkerta))
  Bierhuis_Rotterdam.setNeighbors(Vector( "kampin yläkertaan" -> Kampin_yläkerta))
  Pub_Ikkuna.setNeighbors(Vector(         "narinkkatorille" -> Narinkkatori))
  Henrys_pub.setNeighbors(Vector(         "narinkkatorille" -> Narinkkatori, "veskiin" -> VeskiHenry))
  Shaker.setNeighbors(Vector(             "fredrikinkadulle" -> Fredrikinkatu))
  WilliamK.setNeighbors(Vector(           "fredrikinkadulle" -> Fredrikinkatu))
  AussieBar.setNeighbors(Vector(          "tennispalatsinaukiolle" -> Tennispalatsinaukio))
  Teerenpeli.setNeighbors(Vector(         "tennispalatsinaukiolle" -> Tennispalatsinaukio))
  VessaKamppi.setNeighbors(Vector(        "kampin alakertaan" -> Kampin_alakerta, "vessanpönttöön" -> VeskiHenry))
  VeskiHenry.setNeighbors(Vector(         "henry's pubiin" -> Henrys_pub, "vessanpönttöön" -> VessaKamppi))
  Circus.setNeighbors(Vector(             "narinkkatorille" -> Narinkkatori))
  alkupaikka.setNeighbors(Vector(         "kampin alakertaan" -> Kampin_alakerta))
  
  /* Luodaan pelihahmo ja asetetaan se "alkupaikkaan". Alkupaikassa pelaaja valitsee ryhmän, jonka jälkeen hahmo siirtyy Kampin alakertaan
   ja peli alkaa */
  val player = new Player(alkupaikka)
  alkupaikka.description = player.help()
  // luodaan muuttuja a, jotta sitä voidaan käyttää sijaintina ryhmien jäseniä luotaessa
  val a = this.Kampin_alakerta
  
  val vessahädänMäärä = 12
  
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
  private val mysteeriMies = new Person("mysteerimies",Teerenpeli,"Uskaltaako kloppi pelata kierroksen Hirsipuuta?\n\nVastaa haasteeseen kyllä tai ei.")
  private val make = new Person("make",Shaker,"Nyt on paha olla.")
  // lisätään henkilöt henkilöhakemistoon
  player.addHenkiloita(Vector(spusse.name -> spusse, puustinen.name -> puustinen, mysteeriMies.name -> mysteeriMies, make.name -> make))
  //lisätään henkilöt paikkoihinsa
  for(tyypit <- player.henkilot.keys) {
    player.henkilot(tyypit).sijainti.lisaaHenkilo(player.henkilot(tyypit))
  }
  // luodaan ryhmät, joissa jokaisessa on 3 jäsentä
  val group1 = new Group("paatuneet tutalaiset", Vector[Person](rontti,akseli,kymis),9,300,40)
  val group2 = new Group("syntiset kylterit",Vector[Person](janpaul,christoffer,erik),7,300,100000)
  val group3 = new Group("viattomat infolaiset",Vector[Person](henrik,ville,tiina),5,300,30)
  val group4 = new Group("tutalaiset", Vector[Person](rontti,akseli,kymis),9,300,40)
  val group5 = new Group("kylterit",Vector[Person](janpaul,christoffer,erik),7,300,1000000)
  val group6 = new Group("infolaiset",Vector[Person](henrik,ville,tiina),5,300,30)
  
  
  //lisätään edellä luodut ryhmät pelaajan valittavissa oleviin ryhmiin
  this.player.addGroups(Vector(group1.nimi -> group1, group2.nimi -> group2, group3.nimi -> group3, group4.nimi -> group4, group5.nimi -> group5, group6.nimi -> group6))
  
  // lista kaikista baareista
  private val baarit = Vector[Area](Bruuveri, AussieBar, Bierhuis_Rotterdam, Pub_Ikkuna, Henrys_pub, Shaker, WilliamK, Teerenpeli)
  
  //lisätään jokaisen baarin tarjontaan olut ja viina
  baarit.foreach(_.addDrink(new Drink("talon olut","Mainiota Olvi kolmosta opiskelijahintaan","Tilasit oluen. Joit huurteisen yhdellä kulauksella!",5)))
  baarit.foreach(_.addDrink(new Drink("viina","Räväkkä Ko-ko-ko-koskeeen ko-ko-ko-korvaaaa tyydyttää janoisemmankin teekkarin","Joit viinan ja irvistit kuin fuksi.",3)))
  

  var juodut = player.juodut
  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  var minuutitJaljella = if((this.player.maximiaika - turnCount*10) % 60 == 0) "00" else ((this.player.maximiaika - turnCount*10) % 60)
  var aikaaJaljella = (this.player.maximiaika - turnCount*10)/60 + ":" + minuutitJaljella


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = this.player.location == this.destination && this.player.rahat > 0 && this.player.juodut >= this.player.vaadittukanni &&  turnCount*10 <= player.maximiaika

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */ 
  def isOver = this.isComplete || this.player.hasQuit || this.player.rahat < 0 || this.turnCount*10 > this.player.maximiaika || this.player.vessahata >= vessahädänMäärä

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
      "Tervetuloa jatkoille! Keräsit " +  this.player.juodut + " leimaa, joten saat haalarimerkin!"
    else if (this.turnCount*10 > this.player.maximiaika)
      "Et ehtinyt jatkoille asti ajoissa. Yritä ensi vuonna uudestaan!"
    else if (this.player.vessahata >= vessahädänMäärä)
      "Pissasit housuun! Koko Helsinki nauraa sinulle ja joudut poistumaan häpeissaän takaisin Otaniemen suojiin."
    else if (this.player.rahat < 0)
      "Senkin lurppasuu, joit kaikki rahasi ja joudut lähtemään kotiin odottamaan ensi kuun tukia."
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
      if(command.toLowerCase.take(4) == "mene" || command.toLowerCase.take(5) == "tilaa") {
      this.turnCount += 1 
    }}
    var vessa = ""
    if(this.player.vessahata > (vessahädänMäärä - 5) && !isOver )vessa = "\n\nSinulla alkaa olla jo melko kova vessahätä, varo ettet lirauta housuun!"
    outcomeReport.getOrElse("Olet jo niin humalassa, ettei puheestasi saa selvää. \nSanoppa uudestaan.") + vessa
  }
  
/**
  TODO

  joku ansa vielä, esim jos jää puhumaan jollekkin , ni hävii pelin
  taustamusiikit (ulkoilma, baari, voitto)
  																																															DONE baareihin tyypit näkyviin
  lisää tyyppejä ja juomia
  																																															DONE katso-metodi (kertoo drinkkien kuvaukset)
  pitää laskea hyvät drinkki- ja aikavaatimukset ja hyvä vessahätämäärä
  																																															DONE vessahädästä varoitus kun esim 20/30
  																																															DONE guihin näkyviin montako juomaa juonut  kautta tavoite
  																																															DONE ryhmäläisten nimet näkyviin alussa ja help metodilla
  																																															DONE makenhaku
  vessassakäymismetodi
  																																															DONE Drink-luokalle hinta ja tilaa-metodi vähentämään raheja
  																																															DONE guihin rahevarat
  helppi selkeemäks ja lisää hauskuuttaa teksteihin jos ehtii
  ylimääräisten adventuresta jääneiden metodien karsinta
  uusi nimi
  walkthrough
  kartta selkeämmäksi tai hienommaksi?
  vessareitti pois?
  ((vektori, mistä menemisfraasi randomilla.))
**/
}