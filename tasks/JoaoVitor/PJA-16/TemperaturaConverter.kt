fun main() {
    printFinalTemperature(27.0, "celsius", "fahrenheit", {27.0 * 9/5 + 32.0})
    printFinalTemperature(350.0, "kelvin", "celsius", {350.0 - 273.15})
    printFinalTemperature(10.0, "fahrenheit", "kelvin", {5.0/9.0 * (10.0 - 32.0) + 273.15})
}


fun printFinalTemperature(
    initialMeasurement: Double, 
    initialUnit: String, 
    finalUnit: String, 
    conversionFormula: (Double) -> Double
) {
    val finalMeasurement = String.format("%.2f", conversionFormula(initialMeasurement)) // two decimal places
    println("$initialMeasurement degrees $initialUnit is $finalMeasurement degrees $finalUnit.")
}