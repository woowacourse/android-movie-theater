package woowacourse.movie.activity.reservationresult

interface ReservationResultContract {
    interface View {
        var presenter: Presenter
        fun setUpUiModels()
    }

    interface Presenter
}
