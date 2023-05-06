package woowacourse.movie.ui.main.cinemaBottomSheet

import com.example.domain.model.Cinema
import com.example.domain.repository.CinemaRepository
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class CinemaListPresenter(
    private val view: CinemaListContract.View,
    private val repository: CinemaRepository = CinemaRepository
) : CinemaListContract.Presenter {
    override fun getCinemaList(movie: MovieState) {
        view.setAdapter(repository.findCinema(movie.asDomain()).map(Cinema::asPresentation))
    }
}
