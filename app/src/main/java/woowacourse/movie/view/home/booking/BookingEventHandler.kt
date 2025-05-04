package woowacourse.movie.view.home.booking

interface BookingEventHandler {
    fun onIncreasePeopleCount()

    fun onDecreasePeopleCount()

    fun onBookingComplete()
}
