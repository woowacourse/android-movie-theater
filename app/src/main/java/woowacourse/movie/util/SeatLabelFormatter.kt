package woowacourse.movie.util

object SeatLabelFormatter {
    private val alphabets = ('A'..'Z').toList()

    fun formatLabel(
        row: Int,
        column: Int,
    ): String {
        val rowChar = alphabets.getOrNull(row - 1) ?: '?'
        return "$rowChar$column"
    }

    fun parseLabel(label: String): Pair<Int, Int>? {
        val basic =
            label
                .trim()
                .uppercase()

        val rowChar = basic.getOrNull(0) ?: return null
        val row = (rowChar - 'A') + 1
        val col = basic.substring(1).filter { it.isDigit() }.toIntOrNull() ?: return null

        return Pair(row, col)
    }
}
