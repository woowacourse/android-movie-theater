package woowacourse.movie.ui.main.cinemaBottomSheet

import com.example.domain.model.Cinema
import com.example.domain.repository.CinemaRepository
import com.example.domain.repositoryImpl.CinemaSampleRepository
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class CinemaListPresenter(
    private val view: CinemaListContract.View,
    private val repository: CinemaRepository = CinemaSampleRepository,
    private val movie: MovieState
) : CinemaListContract.Presenter {
    override fun setUpCinemaList() {
        view.setCinemaList(
            repository.findCinema(movie.asDomain()).map(Cinema::asPresentation),
            movie
        )
    }
}
