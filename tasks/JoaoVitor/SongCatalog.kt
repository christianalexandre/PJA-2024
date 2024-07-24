class song(val titulo: String, val artista: String, val agePub: Int, val reproducoes: Int) {
	fun popular(): Boolean{
        if(reproducoes < 1000){
            return false
        }else{
            return true
        }
    }
    fun printDescricao(){
        println("$titulo, interpretado por $artista, foi lançado em $agePub")
    }
}