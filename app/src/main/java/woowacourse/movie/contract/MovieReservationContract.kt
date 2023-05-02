package woowacourse.movie.contract

interface MovieReservationContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter
}
