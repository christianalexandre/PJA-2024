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
            print(" Doesn't have a referrer.")
        } else if(referrer.hobby == null){
            print(" Has a referrer named ${referrer.name}, who doesn't have a hobbie.")
        } else {
            print(" Has a referrer named ${referrer.name}, who likes to ${referrer.hobby}.")
        }
        
        println("\n")
    }
}

fun main() {
    
    	val gabriel = Person("Gabriel", 17, "play games", null)
        val daniel = Person("Daniel", 21, null, gabriel)
        val nicolas = Person("Nicolas", 30, "draw", daniel)
        val giulia = Person("Giulia", 18, "do the nails", nicolas)
        val maira = Person("Maira", 16, "design", null)
    
    val people = listOf(gabriel, daniel, nicolas, giulia, maira)
    
    people.forEach {
        it.showProfile()
    }
}