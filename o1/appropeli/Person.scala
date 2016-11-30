package o1.appropeli

// Person -luokka edustaa kaikkia pelin henkilöitä.

class Person(val name: String, var sijainti: Area, val tunnuslause: String) {
  
  override def toString = this.name.capitalize
  
}