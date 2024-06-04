fun main() {
    
    val joao = Person("João", 33, "play football", null)
    val pedro = Person("Pedro", 28, "climb", joao)
    val messi = Person("Messi", 36, "play football", pedro)
    val cris = Person("Cris", 37, "play tennis", messi)
    val maria = Person("Maria", 22, "dance", null)
    
    val perfil = listOf(joao, pedro, messi, cris, maria)
    
    perfil.forEach {
        it.showProfile()
    }
}
class Person(val name: String, val age: Int, val hobby: String?, val referrer: Person?) {
    fun showProfile() {
        println("Name: ${this.name}")
        println("Age: ${this.age}")
        
        if(hobby != null) {
            print("Likes to ${this.hobby}.")
        } else {
            print("Doesn't have a hobby.")
        } 
        
        if(referrer == null) {
            print(" Doesn't have a referrer. \n")
        } else if(referrer.hobby == null){
            print(" Has a referrer named $referrer.name, who doesn't have a hobbie. \n")
        } else {
            print(" Has a referrer named $referrer.name, who likes to $referrer.hobby.\n")
        }
    }
}