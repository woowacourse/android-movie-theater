package domain

class Count(val value: Int) {
    operator fun plus(number: Int): Count = Count(value + number)

    operator fun minus(number: Int): Count {
        val result = value - number
        if (result <= 0) return Count(value)
        return Count(result)
    }
}
