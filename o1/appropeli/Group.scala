package o1.appropeli

class Group(val nimi: String, val jasenet: Vector[Person], val juomaMaara: Int, val aikaMaara: Int, val rahaMaara: Int) {
  

  override def toString = nimi
  def kuvaus = "Tämän ryhmän jäseniä ovat: " + jasenet.mkString(", ")

}