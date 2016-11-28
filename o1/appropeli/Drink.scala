package o1.appropeli

class Drink(val name: String, val kuvaus: String, val tilaamisenJalkeen: String, val hinta: Int) {
  
  /** Returns a short textual representation of the item (its name, that is). */
  override def toString = this.name
  
  
}