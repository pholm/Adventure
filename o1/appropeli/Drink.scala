package o1.appropeli

// Drink -luokka edustaa pelin juomia.
// Jokaisella juomalla on nimi, kuvaus, hinta, ja juomisen jälkeinen fraasi

class Drink(val name: String, val kuvaus: String, val tilaamisenJalkeen: String, val hinta: Int) {
  
  override def toString = this.name 
  
  
}