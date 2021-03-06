package o1.appropeli

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer
import scala.io.Source
import o1.sound._
import util.Random
  


// Player -luokka edustaa pelin pelaajan ohjailemaa pelihahmoa. 



class Player(startingArea: Area) {

  private var currentLocation = startingArea      // tämän hetkinen sijainti, gatherer
  private var quitCommandGiven = false            // kertoo onko pelaaja luovuttanut, one way flag
  private var ryhmä: Option[Group] = None         // pelaajan ryhmä
  var juodut = 0                                  // pitää kirjaa pelaajan juoduista juomista
  var vessahata = 0                               // pitää kirjaa vessahädästä
  var vaadittukanni = 0                           // pelaajan vaadittu humalatila
  var maximiaika = 0                              // pelin maksimiaika
  var rahat = 0                                   // pelaajan rahat
  var porukat = Map[String, Group]()              // pelin ryhmät
  var henkilot = Map[String,Person]()             // pelin henkilöt
  
  private var soitettu = false                    // Maken etsimiseen
  var makeLoytynyt = true                         // Maken etsimiseen
  
  private var haasteheitetty = false                                                                                // Mysteerimiehen hirsipuuhun
  private var vaarinmennyt = 0                                                                                      // Mysteerimiehen hirsipuuhun
  private var maxVaara = 5                                                                                          // Mysteerimiehen hirsipuuhun
  private var piilosana  = Vector[String] ("___", "___", "___" , "___",  "___", "___", "___", "___", "___")         // Mysteerimiehen hirsipuuhun
  private var tavoitesana= Vector[String] (" H ", " U ", " M " , " A ",  " N ", " I ", " S ", " T ", " I ")         // Mysteerimiehen hirsipuuhun
  
  def addGroups(ryhmat: Vector[(String, Group)]) = this.porukat ++= ryhmat                                          // lisää ryhmiä peliin
  def addHenkiloita(henkilot: Vector[(String,Person)]) = this.henkilot ++= henkilot                                 // lisää henkilöitä peliin
  
  //valitsee ryhmän pelin alussa
  def valitsen(ryhmanNimi: String) = {
    if(!this.ryhmä.isDefined) {
      if(porukat.contains(ryhmanNimi)) {
        this.ryhmä = Some(porukat(ryhmanNimi))
        this.vaadittukanni = porukat(ryhmanNimi).juomaMaara
        this.maximiaika = porukat(ryhmanNimi).aikaMaara
        this.rahat = porukat(ryhmanNimi).rahaMaara
         var ryhmanNimet = Buffer[String]()
        for(tyypit <- ryhmä.get.jasenet) { ryhmanNimet += tyypit.name }
        addHenkiloita(ryhmanNimet.toVector.zip(porukat(ryhmanNimi).jasenet)) 
        this.currentLocation = this.location.neighbor("kampin alakertaan").get
        "Valitsit " + ryhmanNimi.capitalize + "!\n\n" + porukat(ryhmanNimi).kuvaus
      } else "Valitettavasti et ole käynyt tarpeeksi pöhisemässä verkostoitumistapahtumissa, joten nämä ovat ainoat mahdollisuutesi. Valitse tietenkin (?) Tutalaiset, jos et osaa päättää!"
    } else "Tiedetään, ryhmävalintasi ei ollut nappiosuma, mutta olisi todella epäkohteliasta vaihtaa porukkaa kesken appron."
  }
  
  //puhuu ihmisille samassa tilassa
  def puhu(personName: String) = {
   val puhumisfraasit = Vector[String](" sanoo: "," tokaisee: "," vastaa: "," kommentoi: ")
   var randomi = new Random
   if(henkilot.contains(personName)) {
     if(henkilot(personName).sijainti == this.location) {
       if(personName == "mysteerimies") {
       haasteheitetty = true
       henkilot(personName).tunnuslause
       }
       personName.capitalize + puhumisfraasit(randomi.nextInt(puhumisfraasit.size)) + henkilot(personName).tunnuslause
     } else "Ei näy kaveria missään, ehkä hän on seuraavassa kapakassa!"     
   } else "Ei ole kyllä tuommoisesta kaverista kuullut edes herra Peter 'network' Kelly."  
 }

   // vastaus Mysteerimiehelle
  def kyllä () = {
    "Mainiota, rakastan ihmisiä jotka tykkäävät pelata.\nArvaa kirjain kerrallaan mitä maailman huvittavinta kahdeksankirjaimista asiaa ajattelen." + 
    "\n\nSinulla on 4 väärää vastausta käytettävänäsi, voittaja tarjoaa häviäjälle oluen!\n" + 
    piilosana.mkString(" ") +
    "\n(arvaa sanomalla 'arvaan [kirjain]'"    
  }
  // vastaus Mysteerimiehelle
  def ei () = {
    haasteheitetty = false
    "Ei sitten, senkin mammanpoika!"
  }
  
  //apumetodi hirsipuuhun
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
  
  //arvaa kirjaimen hirsipuussa Mysteerimiestä vastaan
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
        this.juodut += 1
        this.vessahata += 1
        haasteheitetty = false
        tavoitesana.mkString(" ") + "\n\nOhhoh, en olisi uskonut, että kykenet tämän ratkaisemaan! Olet oluesi ansainnut!" +
        "\n(Voitit mysteerimiehen haasteen ja sait ylimääräisen oluen!)"
      }} 
   
  }else "Mitä se siinä yksikseen arvailee."
}

  //kertoo onko pelaaja lopettanut
  def hasQuit = this.quitCommandGiven

  
  //antaa nykyisen sijainnin
  def location = this.currentLocation
  

  
  //liikkuu pelialueella
  def mene (direction: String) = {
    if(location.name == "Kampin vessa" && direction == "nukkumaan") {
      val destination = this.location.neighbor("kampin alakertaan").get.neighbor("kampin yläkertaan").get.neighbor("tennispalatsinaukiolle").get.neighbor("fredrikinkadulle").get.neighbor("william koohon").get.neighbor("veskiin")
      this.currentLocation = destination.get
      this.ryhmä.foreach(_.jasenet.foreach(_.sijainti = destination.get))
      "Kuukahdit pisuaarin alle humalassa kuin lukiolainen konsanaan. Et muista mitään viime hetkistä, mutta saat kuulla porukkasi kantaneen sinua mukana ympäri kaupunkia." + 
      "\n\nEt muista mitään viime hetkistäsi, mutta nyt kännisi on laskenut sen verran, että huomaat makaavasi William Koon vessan lavuaarien päällä. Kerää itsesi ja jatka matkaa!"
    }
    else {
    val menemisfraasit = Vector[String]("Menit ", "Saavuit ", "Tulit ", "Kävelit ")
    var randomi = new Random
    if(haasteheitetty == false){
    if(this.ryhmä.isDefined) {
    val nykysijainti = this.currentLocation
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation) 
    if (destination.isDefined) {
      if(this.location.musa.isDefined) {
      playRecording(this.location.musa.get,1)
      }     
      this.ryhmä.foreach(_.jasenet.foreach(_.sijainti = destination.get))      
      nykysijainti.onkoKayty = false
      if(this.location.name == "Kampin vessa" || this.location.name == "William Koon vessa") {
        var kamppi = " "
        if(this.location.name == "Kampin vessa") kamppi = " \n\nJostain syystä tunnet kuitenkin voimakasta halua Mennä Nukkumaan..."
       if(this.vessahata > 10) {
         this.vessahata = 0
         "Huh, ehdit paikalle viimeisillä hetkillä juuri ennen kuin olit ratkeamassa. Nyt voit taas turvallisin mielin jatkaa iltaa. " + kamppi
       }else {
         this.vessahata = 0
         "Ei sinulla vielä kauhea hätä ollut, mutta varmistelu on järkevää!" + kamppi
       }
      }
      else  {
      this.vessahata += 1 
      if(juodut >= 4 && soitettu == false) {
        soitettu = true
        makeLoytynyt = false
        playRecording("nokia-tune.wav",3)
        "PUHELIMESI SOI, VASTAAT: \n'Shakerin baarimikko täällä terve. Joku Make-niminen kaverisi makaa tällä hetkellä pää pöntössä meidän vessassa, ja osasi antaa ainoastaan sun puhelinnumeron. Tulisitko hakemaan törvelön pois?'" +
        "\n\nKäy noutamassa Make Shakerista ennen kuin jatkat iltaasi Circuksessa."
      } else {
        if(soitettu == true && location.name == "Shaker") {
        makeLoytynyt = true
        "Huh, nappasit Maken kiinni juuri kun hän oli konttaamassa ulos ikkunasta. Voit nyt jatkaa iltaasi rauhassa!"      
    }  else menemisfraasit(randomi.nextInt(menemisfraasit.size)) + direction + "." 
      }}} else "Et voi mennä " + direction + "." + " Aikaasi kului suuntaa etsiessä."
    } else "Yritit livahtaa kaveriesi ohi, mutta viime hetkellä aina kärppänä oleva Antti 'rontti' Ihalainen bongaa sinut. Joudut siis valitsemaan jonkun ryhmistä."
  } else "Älä yritä luikkia pakoon mysteerimiestä!\nVastaa pyyntöön kyllä tai ei."
 }}   
  
  
  /** Signals that the player wants to quit the game. Returns a description of what happened within 
    * the game as a result (which is the empty string, in this case). */
  def luovutan() = {
    this.quitCommandGiven = true
    ""
  }
  
  //tutkii juoman etikettiä
  def katso(juoma: String) = {
    this.location.giveDrink(juoma).kuvaus
  }
  
  // tilaa baareissa juoman
  def tilaa(juoma: String) = {
    if (location.onkoKayty == false) {
      if (this.location.giveDrink(juoma) != this.location.giveDrink("tyhja")) {                        // Kaikki listan määritellyt juomat
        this.juodut += 1
        this.vessahata += 2
        this.rahat -= this.location.giveDrink(juoma).hinta
        location.onkoKayty = true
        this.location.giveDrink(juoma).tilaamisenJalkeen      
      } else this.location.giveDrink(juoma).kuvaus
    } else "Kaveri hei, sun takana on kuuskymmentä janoista teekkaria, jatka matkaa jo!"
  }
  
  //avaa helpin
  def help() = {    
    type Closeable = { def close(): Unit }
    def useAndClose[Resource <: Closeable, Result](resource: Resource)(operation: Resource => Result) = {
  try {
    operation(resource)
  } finally {
    resource.close()
  }
  }   
    var onkoRyhmä = " "
    if(ryhmä.isDefined) onkoRyhmä  = "\n\n" + ryhmä.get.kuvaus
    val tiedosto = Source.fromFile("README.md")
    useAndClose(tiedosto)( _.getLines.toVector ).mkString("\n") + onkoRyhmä
  }
 
}






