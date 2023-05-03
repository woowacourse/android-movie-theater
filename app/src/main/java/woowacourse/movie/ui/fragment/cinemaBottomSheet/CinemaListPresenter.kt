package woowacourse.movie.ui.fragment.cinemaBottomSheet

import woowacourse.movie.data.CinemaRepository
import woowacourse.movie.model.CinemaState

class CinemaListPresenter(
    private val repository: CinemaRepository = CinemaRepository
) : CinemaListContract.Presenter {
    override fun getCinemaList(): List<CinemaState> {
        return repository.allCinema()
    }
}
