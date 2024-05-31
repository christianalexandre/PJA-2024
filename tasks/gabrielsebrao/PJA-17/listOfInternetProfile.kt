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
    val people = listOf(
        Person("Gabriel", 17, "play games", null),
        Person("Daniel", 21, null, null),
        Person("Nicolas", 30, "draw", null),
        Person("Giulia", 18, "do the nails", null),
        Person("Maira", 16, "design", null)
    )
    
    people.forEach {
        it.showProfile()
    }
}