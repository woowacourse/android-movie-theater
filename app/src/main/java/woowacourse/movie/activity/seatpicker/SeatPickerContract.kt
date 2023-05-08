package woowacourse.movie.activity.seatpicker

interface SeatPickerContract {
    interface View {
        var presenter: Presenter

        fun initSeat()
        fun setMovieTitle(title: String)
        fun setTicketPrice(price: String)
    }

    interface Presenter
}
