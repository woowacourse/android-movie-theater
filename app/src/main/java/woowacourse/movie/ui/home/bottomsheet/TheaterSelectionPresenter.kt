package woowacourse.movie.ui.home.bottomsheet

import woowacourse.movie.data.theater.TheaterRepository

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val repository: TheaterRepository,
) : TheaterSelectionContract.Presenter {
    override fun setTheaterList() {
        val theaters = repository.getData()
        view.setTheaterList(theaters)
    }
}
