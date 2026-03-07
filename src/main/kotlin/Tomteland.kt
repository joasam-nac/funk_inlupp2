package inlamningsuppgift2.joakim

class Tomteland {

    private val nordpolen: Map<String, List<String>> = mapOf(
        "Tomten" to listOf("Glader", "Butter"),
        "Glader" to listOf("Tröger", "Trötter", "Blyger"),
        "Butter" to listOf("Rådjuret", "Nyckelpigan", "Haren", "Räven"),
        "Tröger" to emptyList(),
        "Trötter" to listOf("Skumtomten"),
        "Blyger" to emptyList(),
        "Skumtomten" to listOf("Dammråttan"),
        "Dammråttan" to emptyList(),
        "Rådjuret" to emptyList(),
        "Nyckelpigan" to emptyList(),
        "Haren" to emptyList(),
        "Räven" to listOf("Gråsuggan", "Myran"),
        "Gråsuggan" to emptyList(),
        "Myran" to listOf("Bladlusen"),
        "Bladlusen" to emptyList()
    )

    fun getUnderlings(currentName: String, res: MutableList<String>): List<String> {
        require(currentName in nordpolen) { "Could not find $currentName" }

        // hämta alla som jobbar under namn
        val directUnderlings = nordpolen[currentName].orEmpty()

        // hämta alla som jobbar under de som jobbar under namn
        for (underling in directUnderlings) {
            res.add(underling)
            getUnderlings(underling, res)
        }

        return res
    }
}