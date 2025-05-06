package woowacourse.movie.view.home.booking

interface BookingEventHandler {
    fun onIncreaseAdmissionCount()

    fun onDecreaseAdmissionCount()

    fun onBookingComplete()
}
