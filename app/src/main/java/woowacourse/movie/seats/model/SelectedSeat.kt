package woowacourse.movie.seats.model

data class SelectedSeat(
    val seat: Seat,
    var isSelected: Boolean = false,
)
