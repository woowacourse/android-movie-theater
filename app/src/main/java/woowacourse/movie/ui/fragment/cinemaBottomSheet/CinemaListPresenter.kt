package woowacourse.movie.ui.fragment.cinemaBottomSheet

import woowacourse.movie.data.CinemaRepository

class CinemaListPresenter(
    private val view: CinemaListContract.View,
    private val repository: CinemaRepository = CinemaRepository
) : CinemaListContract.Presenter {
    override fun getCinemaList() {
        view.setAdapter(repository.allCinema())
    }
}
