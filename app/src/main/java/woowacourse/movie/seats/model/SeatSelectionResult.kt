package woowacourse.movie.seats.model

sealed class SeatSelectionResult {
    object Success : SeatSelectionResult()

    object Failure : SeatSelectionResult()

    object MaxCapacityReached : SeatSelectionResult()

    object AlreadyMaxCapacityReached : SeatSelectionResult()
}
