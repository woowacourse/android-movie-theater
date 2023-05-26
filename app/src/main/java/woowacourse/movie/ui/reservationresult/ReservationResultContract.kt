package woowacourse.movie.ui.reservationresult

interface ReservationResultContract {
    interface View {
        var presenter: Presenter
        fun setUpUiModels()
    }

    interface Presenter
}
