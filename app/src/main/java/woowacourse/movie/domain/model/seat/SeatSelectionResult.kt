package woowacourse.movie.domain.model.seat

sealed class SeatSelectionResult {
    object Success : SeatSelectionResult()

    object Failure : SeatSelectionResult()

    object MaxCapacityReached : SeatSelectionResult()

    object AlreadyMaxCapacityReached : SeatSelectionResult()
}
