open class Phone(var isScreenLightOn: Boolean = false){
    open fun switchOn() {
        isScreenLightOn = true
    }
    
    open fun switchOff() {
        isScreenLightOn = false
    }
    
    fun checkPhoneScreenLight() {
        val phoneScreenLight = if (isScreenLightOn) "on" else "off"
        println("The phone screen's light is $phoneScreenLight.")
    }
}

class FoldablePhone(var isPhoneFolded: Boolean = true) : Phone() {
    override fun switchOn() { 
        if (!isPhoneFolded) super.isScreenLightOn = true 
    }
    
    fun foldPhone() {
        isPhoneFolded = true
    }
    
    fun unfoldPhone() {
        isPhoneFolded = false
    }
}

fun main() {
    val phone: FoldablePhone = FoldablePhone()
    phone.unfoldPhone() // se essa função não for chamada, não ligará a tela 
    phone.switchOn()
    phone.checkPhoneScreenLight()
}