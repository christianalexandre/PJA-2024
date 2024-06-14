class Student(val name: String, val age:Int) {
    constructor(name: String): this(name, 15) {
        println("Construtor 1 chamado.")
    }
    constructor(age: Int): this("Student", age) {
        println("Construtor 2 chamado.")
    }
    constructor(): this("Student", 15) {
        println("Construtor 3 chamado.")
    }
    
    init {
        println("Init chamado.")
    }
    
    fun takeName(): String {
        return this.name
    }
}

fun main() {
    val student: Student = Student()
   	println(student.takeName())
}