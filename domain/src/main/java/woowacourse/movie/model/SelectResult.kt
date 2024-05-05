package woowacourse.movie.model

sealed class SelectResult(val value: SelectedSeats) {
    class Exceed(selectedSeats: SelectedSeats) : SelectResult(selectedSeats)

    class LessSelect(selectedSeats: SelectedSeats) : SelectResult(selectedSeats)

    class AlreadyReserve(selectedSeats: SelectedSeats) : SelectResult(selectedSeats)

    class Success(selectedSeats: SelectedSeats) : SelectResult(selectedSeats)
}
