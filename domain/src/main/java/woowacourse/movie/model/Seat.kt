package woowacourse.movie.model

import java.io.Serializable

data class Seat(
    val rate: Tier,
    val row: Int,
    val col: Int,
) : Serializable {
    val price = rate.price.price

    override fun toString(): String {
        val rateStr =
            when (this.rate) {
                Tier.S -> "S"
                Tier.A -> "A"
                else -> "B"
            }
        return "$rateStr|$row|$col"
    }

    companion object {
        fun from(seatString: String): Seat {
            val splited = seatString.split("|")
            val rate =
                when (splited[0]) {
                    "S" -> Tier.S
                    "A" -> Tier.A
                    else -> Tier.B
                }
            val row = splited[1].toInt()
            val col = splited[2].toInt()
            return Seat(rate, row, col)
        }
    }
}
