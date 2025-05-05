package woowacourse.movie.presentation.booking

interface BookingEventHandler {
    fun onIncreaseButtonClicked()

    fun onDecreaseButtonClicked()

    fun onConfirmButtonClicked()
}
