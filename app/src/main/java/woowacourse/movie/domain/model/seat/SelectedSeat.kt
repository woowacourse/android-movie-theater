package woowacourse.movie.domain.model.seat

data class SelectedSeat(
    val seat: Seat,
    var isSelected: Boolean = false,
)
