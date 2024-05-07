package woowacourse.movie.model

import woowacourse.movie.util.Formatter.formatPrice

class MovieSelectedSeats(
    val count: Int = DEFAULT_SELECTED_SEATS_COUNT,
    rowSize: Int = DEFAULT_ROW_SIZE,
    private val columnSize: Int = DEFAULT_COLUMN_SIZE,
) {
    private val baseSeats =
        List(rowSize * columnSize) { index ->
            val row = index / columnSize
            val column = index % columnSize
            MovieSeat(row, column)
        }

    private val _selectedSeats: MutableSet<MovieSeat> = mutableSetOf()
    val selectedSeats: Set<MovieSeat>
        get() = _selectedSeats.sortedWith(compareBy({ it.row }, { it.column })).toSet()

    fun selectSeat(seat: MovieSeat) {
        _selectedSeats.add(seat)
    }

    fun unSelectSeat(seat: MovieSeat) {
        _selectedSeats.remove(seat)
    }

    fun isSelected(index: Int): Boolean = baseSeats[index] in selectedSeats

    fun totalPriceToString(): String {
        if (selectedSeats.isEmpty()) return "0"
        return selectedSeats.sumOf { selectedSeat -> selectedSeat.grade.price }.formatPrice()
    }

    fun isSelectionComplete(): Boolean = count == selectedSeats.size

    fun getSelectedPositions(): IntArray {
        return selectedSeats.map { seat ->
            seat.row * columnSize + seat.column
        }.toIntArray()
    }

    fun getBaseSeats(): List<MovieSeat> = baseSeats

    companion object {
        private const val DEFAULT_SELECTED_SEATS_COUNT = 1
        private const val DEFAULT_ROW_SIZE = 5
        private const val DEFAULT_COLUMN_SIZE = 4
    }
}
