package o1.appropeli

class Drink(val name: String, val description: String, val tilaamisenJalkeen: String) {
  
  /** Returns a short textual representation of the item (its name, that is). */
  override def toString = this.name
  
  
}