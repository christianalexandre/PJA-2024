class Song(val title: String, val artist: String, val year_published: Int, val play_count: Int) {
    fun isPopular():Boolean {
        if(play_count < 1000) {
            return false
        } else {
            return true
        }
    }
    
    fun printSongDescription() {
        println("$title, performed by $artist, was released in $year_published.")
    }
}

fun main() {
    val song1: Song = Song("Money Trees", "Kendrick Lamar", 2012, 1455800621)
   	song1.printSongDescription()
    if(song1.isPopular()) {
        println("${song1.title} is popular.")
    } else {
        println("${song1.title} is not popular.")
    }
    
    val song2:Song = Song("Sei la o que", "Zé", 1760, 201)
   	song2.printSongDescription()
    if(song2.isPopular()) {
        println("${song2.title} is popular.")
    } else {
        println("${song2.title} is not popular.")
    }
}