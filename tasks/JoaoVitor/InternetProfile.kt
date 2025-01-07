fun main() {
    val amanda = Person("Amanda", 33, "play tennis", null)
    val atiqah = Person("Atiqah", 28, "climb", amanda)
    
    amanda.showProfile()
    atiqah.showProfile()
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