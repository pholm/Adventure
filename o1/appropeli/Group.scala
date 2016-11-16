package o1.appropeli

class Group(jasenet: Vector[Person]) {
  
  override def toString = "Tämän ryhmän jäseniä ovat: " + jasenet.mkString(", ")
  
}