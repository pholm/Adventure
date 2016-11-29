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
  private val Kampin_alakerta     = new Area("Kampin alakerta", "Olet Kampin alakerrassa. Varo vartijoita pakoon juoksevia teinejä!", None)  
  private val Kampin_yläkerta     = new Area("Kampin yläkerta", "Olet Kampin yläkerrassa.", None) 
  private val Narinkkatori        = new Area("Narinkkatori", "Astuessasi Narinkkatorille viiltävä tuuli iskee kasvoillesi. Hankkiudu äkkiä takaisin sisätiloihin!", None)
  private val Fredrikinkatu       = new Area("Fredrikinkatu", "Olet Fredrikinkadulla.", None)
  private val Tennispalatsinaukio = new Area("Tennispalatsinaukio", "Olet Tennispalatsinaukiolla.", None)
  private val Bruuveri            = new Area("Bruuveri", "Tervetuloa Bruuveriin! Olemme tunnettuja laadukkaista, valtavirrasta erottuvista talon omista oluista ja siidereistä.",Some("music1.wav"))
  private val AussieBar           = new Area("Aussie Bar", "Welcome to Aussie Bar. You don't just visit Aussie Bar, you live it!",Some("music2.wav"))
  private val Bierhuis_Rotterdam  = new Area("Bierhuis Rotterdam", "Tervetuloa Bierhuis Rotterdamiin. Bierhuis on välitön hauskanpidon satama, jossa juttu lentää ja tuopit kolahtelevat.",Some("music3.wav"))
  private val Pub_Ikkuna          = new Area("Pub Ikkuna", "Tervetuloa Pub Ikkunaan. Pub Ikkuna on alkuillan starttipaikka Kampin keskuksessa.",Some("music4.wav")) 
  private val Henrys_pub          = new Area("Henry's Pub", "Tervetuloa Henry's Pubiin. Henry's Pub tarjoaa elävää musiikkia maanantaista torstaihin sekä tietenkin erinomaisia juomia moneen makuun.",Some("music5.wav"))
  private val Shaker              = new Area("Shaker", "Tervetuloa Shakeriin. Olemme tunnettu hyvistä cocktaileista, ammattitaitoisesta henkilökunnasta, hyvästä palvelusta ja rennosta ilmapiiristä.",Some("music6.wav"))
  private val WilliamK            = new Area("William K", "Kun haluat treffata ennen leffaa tai mennä fiilistelemään leffan jälkeen, William K. Fredrikinkatu Tennispalatsissa sopii tarkoitukseen kuin buutsit Chuck Norrisille.",Some("music7.wav"))
  private val Teerenpeli          = new Area("Teerenpeli", "Teerenpeli Panimo & Tislaamossa on näyttävällä ja tehokkaalla tavalla yhdistetty oluen valmistus ja tislaaminen.",Some("music8.wav"))
  private val VessaKamppi         = new Area("Kampin vessa", "Olet Kampin vessassa. Tänne ovat monien approt päättyneet ennenaikaisesti.", None)
  private val VeskiWilliam        = new Area("William Koon vessa", "Olet William Koon vessassa.", None)
  private val Circus              = new Area("Circus", "Portsari puhalluttaa sinut eikä todellakaan päästä noin selvää kaveria tylsistyttämään tunnelmaa. \nMene käymään muutamassa kapakassa vielä!", None)
  private val alkupaikka          = new Area("Alkupaikka", "Tervetuloa pelaamaan Appropeliä.", None)
  val destination                 = Circus    

   
  // Määritellään alueille naapurialueet 
  Kampin_alakerta.setNeighbors(Vector(    "kampin yläkertaan" -> Kampin_yläkerta, "vessaan" -> VessaKamppi,"narinkkatorille" -> Narinkkatori))
  Kampin_yläkerta.setNeighbors(Vector(    "bruuveriin" -> Bruuveri, "bierhuis rotterdamiin" -> Bierhuis_Rotterdam, "tennispalatsinaukiolle" -> Tennispalatsinaukio,   "kampin alakertaan" -> Kampin_alakerta))
  Narinkkatori.setNeighbors(Vector(       "pub ikkunaan" -> Pub_Ikkuna, "henry's pubiin" -> Henrys_pub, "tennispalatsinaukiolle" -> Tennispalatsinaukio, "kampin yläkertaan" -> Kampin_yläkerta, "circukseen" -> Circus  ))
  Tennispalatsinaukio.setNeighbors(Vector("teerenpeliin" -> Teerenpeli,"narinkkatorille" -> Narinkkatori,"aussie bariin" -> AussieBar, "fredrikinkadulle" -> Fredrikinkatu, "kampin yläkertaan" -> Kampin_yläkerta   ))
  Fredrikinkatu.setNeighbors(Vector(      "william koohon" -> WilliamK, "shakeriin" -> Shaker, "tennispalatsinaukiolle" -> Tennispalatsinaukio))
  Bruuveri.setNeighbors(Vector(           "kampin yläkertaan" -> Kampin_yläkerta))
  Bierhuis_Rotterdam.setNeighbors(Vector( "kampin yläkertaan" -> Kampin_yläkerta))
  Pub_Ikkuna.setNeighbors(Vector(         "narinkkatorille" -> Narinkkatori))
  Henrys_pub.setNeighbors(Vector(         "narinkkatorille" -> Narinkkatori))
  Shaker.setNeighbors(Vector(             "fredrikinkadulle" -> Fredrikinkatu))
  WilliamK.setNeighbors(Vector(           "fredrikinkadulle" -> Fredrikinkatu, "veskiin" -> VeskiWilliam))
  AussieBar.setNeighbors(Vector(          "tennispalatsinaukiolle" -> Tennispalatsinaukio))
  Teerenpeli.setNeighbors(Vector(         "tennispalatsinaukiolle" -> Tennispalatsinaukio))
  VessaKamppi.setNeighbors(Vector(        "kampin alakertaan" -> Kampin_alakerta))
  VeskiWilliam.setNeighbors(Vector(       "william koohon" -> WilliamK))
  Circus.setNeighbors(Vector(             "narinkkatorille" -> Narinkkatori))
  alkupaikka.setNeighbors(Vector(         "kampin alakertaan" -> Kampin_alakerta))
  
  /* Luodaan pelihahmo ja asetetaan se "alkupaikkaan". Alkupaikassa pelaaja valitsee ryhmän, jonka jälkeen hahmo siirtyy Kampin alakertaan
   ja peli alkaa */
  val player = new Player(alkupaikka)
  alkupaikka.description = player.help()
  // soitetaan aloitusmusiikki
  playRecording("music0.wav",2)
  // luodaan muuttuja a, jotta sitä voidaan käyttää sijaintina ryhmien jäseniä luotaessa
  val a = this.Kampin_alakerta
  
  val vessahädänMäärä = 16
  
  // luodaan peliin henkilöitä. Ryhmiin kuuluvilla henkilöillä alkusijainti on sama kuin pelaajalla. 
  private val rontti = new Person("rontti",a,"olen nousu_humalassa!")
  private val akseli = new Person("akseli",a,"Tää on mun ensimmäinen appro!")
  private val kymis = new Person("kymis",a,"Täytyy varmaan skipata huomiset luennot, taas...")
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
  private val työmies = new Person("työ mies",Henrys_pub,"olvi kolme tekee hyvää työ päivän jälkeen")
  private val risumies = new Person("risumies",WilliamK,"Mie oon ympäristönpaskaaja.")
  private val fabu = new Person("fabu",Bierhuis_Rotterdam,"Voi kuule, sillon kun minä olin fuksi olin tuossa ajassa jo kiertänyt koko Helsingin kaikki baarit")
  private val pirjo = new Person("pirjo",AussieBar,"Mitä se siinä tuijottelee?")
  private val saarnaaja = new Person("saarnaaja",Pub_Ikkuna,"Do you have time to talk about the miracle of Elisa?")
  private val jyrki = new Person("jyrki",AussieBar,"Opettele dokaamaa!")
 
  // lisätään henkilöt henkilöhakemistoon
  player.addHenkiloita(Vector(spusse.name -> spusse, puustinen.name -> puustinen, mysteeriMies.name -> mysteeriMies, make.name -> make,työmies.name -> työmies,risumies.name -> risumies, fabu.name -> fabu, pirjo.name -> pirjo, saarnaaja.name -> saarnaaja,jyrki.name -> jyrki))
  
  //lisätään henkilöt paikkoihinsa
  for(tyypit <- player.henkilot.keys) {
    player.henkilot(tyypit).sijainti.lisaaHenkilo(player.henkilot(tyypit))
  }
  // luodaan ryhmät, joissa jokaisessa on 3 jäsentä
  private val group1 = new Group("paatuneet tutalaiset", Vector[Person](rontti,akseli,kymis),9,360,40)
  private val group2 = new Group("syntiset kylterit",Vector[Person](janpaul,christoffer,erik),7,360,100000)
  private val group3 = new Group("viattomat infolaiset",Vector[Person](henrik,ville,tiina),5,360,30)
  private val group4 = new Group("tutalaiset", Vector[Person](rontti,akseli,kymis),9,360,40)
  private val group5 = new Group("kylterit",Vector[Person](janpaul,christoffer,erik),7,360,1000000)
  private val group6 = new Group("infolaiset",Vector[Person](henrik,ville,tiina),5,360,30)
  
  
  //lisätään edellä luodut ryhmät pelaajan valittavissa oleviin ryhmiin
  this.player.addGroups(Vector(group1.nimi -> group1, group2.nimi -> group2, group3.nimi -> group3, group4.nimi -> group4, group5.nimi -> group5, group6.nimi -> group6))
  
  // lista kaikista baareista
  private val baarit = Vector[Area](Bruuveri, AussieBar, Bierhuis_Rotterdam, Pub_Ikkuna, Henrys_pub, Shaker, WilliamK, Teerenpeli)
  
  // luodaan kaikki pelin drinkit
  private val stinanErikoinen = new Drink("stinan erikoinen","aivan 5.0/5.0","Täydet pisteet yrityksestä kulauttaa yhdellä huikalla!",5)
  private val seksiaRannalla = new Drink("sexiä rannalla", "Poloisen teekkarin (vain) fantasia", "Sujahtipa liukkaasti!",6)
  private val longIsland = new Drink("pitkän saaren jäätee", "Aamulaskareiden unelma","Kylläpä nautiskelit!",7)
  private val kylterinErikoinen = new Drink("kylterin erikoinen","Hivelee makuhermoja sekä lompakkoa","Se tuntui tiukalta! Tosin erityisesti kukkarossa", 15)
  private val askonSponssi = new Drink("askon sponssi", "Mukava olut mukavalta mieheltä", "Nautiskelit tuopillisen rakkaudella.",6)
  private val rommiKola = new Drink("rommikola","Sikajuhlista ylijääneitä itse juomasian tarjoilemana","Kyllä maistui, vaikka toisin kuin sikajuhlissa häppärin aikaan, ei ollutkaan ilmainen.",4)
  private val minttu = new Drink("minttu","Raikas kuin fuksin henkäys","Kylläpä piristi!",3)
  private val jallu = new Drink("jallu","Täällä jallutähden alla,\n lasi täyttyy leikatulla","Täällä jallutähden alla \nkorkeimmalla kukkulalla. \nKatson läpi lasin tyhjän, \nsen täytän uudestaan.",4)
  private val siideri = new Drink("siideri","Sopii hyvin tytöille","Olipa ihana juoma.",5)
  private val pontikka = new Drink("pontikka","Suoraan tiskin alta pelottoman teekkarin juotavaksi", "Oho! Ei mennyt edes näkö!",2)
  private val työjuoma = new Drink("työ juoma","työ miehiltä työ miesten vatsa laukkuihin", "Kulautit ykkö sellä ja aloit jo miettimään että yli opisto on idi ooteille",5)
  private val humanistinErikoinen = new Drink("humanistin erikoinen","Höpötystä koko drinkki","Joit drinkin etkä saa enää töitä valmistumisesi jälkeen",5)
  private val tikkiläisenSpesiaali = new Drink("tikkiläisen spesiaali","juomaIsHyvä = true: Boolean","känni += 1; fiilis += 1",5)
  private val valkkari = new Drink("valkoviini","Talon valkoviini suoraan Gristaf Grunpenbergin tilalta Etelä-Ranskasta","Kylläpä tuli aatelinen olo!",8)
  private val punkku = new Drink("punaviini","Talon punaviini suoraan Gristaf Grunpenbergin tilalta Länsi-Norjasta","Kylläpä tuli aatelinen olo!",8)
  private val skumppa = new Drink("skumppa","Kyltereiden kellarista", "Nääh, ei ollut hyvää. Taitaa olla vain kulissia tuo kyltereiden elämä.",7)
  private val kilju = new Drink("kilju", "Tule käymään takahuoneessa", "Baarimikko johdattaa sinut kellariin, josta saat kiljua niin paljon, kuin pystyt juomaan. \nOn kuitenkin niin pahaa, että kiljut ällötyksestä.",2)
  private val pöhina = new Drink("pöhinäjuoma", "super uber innovatiivinen makuelämys","Joit lasillisen ja sinulle kasvoi pöhinäparta. \nIlmoittauduit lisäksi ensi vuoden Slushiin ja Junctioniin sekä liimasit Maciisi startup-saunan ja -lifen pöhinätarrat.", 5)
  
  //lisätään drinkit baareihin
  Bruuveri.addDrinks(Vector[Drink](jallu,tikkiläisenSpesiaali,pöhina))
  AussieBar.addDrinks(Vector[Drink](minttu,humanistinErikoinen))
  Bierhuis_Rotterdam.addDrinks(Vector[Drink](kylterinErikoinen,valkkari))
  Pub_Ikkuna.addDrinks(Vector[Drink](stinanErikoinen,kilju))
  Henrys_pub.addDrinks(Vector[Drink](longIsland,työjuoma,skumppa))
  Shaker.addDrinks(Vector[Drink](seksiaRannalla,siideri))
  WilliamK.addDrinks(Vector[Drink](askonSponssi,pontikka))
  Teerenpeli.addDrinks(Vector[Drink](rommiKola,punkku))
  
   //lisätään jokaisen baarin tarjontaan lisäksi olut ja viina
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
    "Vaihtoehtoina ovat Viattomat infolaiset, Syntiset kylterit sekä Paatuneet tutalaiset.\n(Ryhmät ovat vaikeusasteeltaan helpoimmasta haastavimpaan.)"
    }

    
  /** Returns a message that is to be displayed to the player at the end of the game. The message 
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
    if (this.isComplete) {
      playRecording("victory.wav",3)
      "Tervetuloa jatkoille! Keräsit " +  this.player.juodut + " leimaa, joten saat haalarimerkin!"
    }
    else if (this.turnCount*10 > this.player.maximiaika)
      "Et ehtinyt jatkoille asti ajoissa. Yritä ensi vuonna uudestaan!"
    else if (this.player.vessahata >= vessahädänMäärä)
      "Pissasit housuun! Koko Helsinki nauraa sinulle ja joudut poistumaan häpeissaän takaisin Otaniemen suojiin."
    else if (this.player.rahat < 0)
      "Senkin lurppasuu, joit kaikki rahasi ja joudut lähtemään kotiin odottamaan ensi kuun tukia."
    else  // game over due to player quitting
      "Fuksivirhe!" 
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
  
}