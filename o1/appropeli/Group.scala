package o1.appropeli

// Group -luokka edustaa pelin eri ryhmiä.

class Group(val nimi: String, val jasenet: Vector[Person], val juomaMaara: Int, val aikaMaara: Int, val rahaMaara: Int) {
  
  override def toString = nimi
  
  def kuvaus = "Tämän ryhmän jäseniä ovat: " + jasenet.mkString(", ")

}