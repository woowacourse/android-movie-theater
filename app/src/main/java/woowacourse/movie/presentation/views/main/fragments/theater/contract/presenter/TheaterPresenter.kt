package woowacourse.movie.presentation.views.main.fragments.theater.contract.presenter

import com.woowacourse.data.repository.theater.TheaterRepository
import woowacourse.movie.domain.model.theater.DomainTheater
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.model.theater.PresentationTheater
import woowacourse.movie.presentation.views.main.fragments.theater.contract.TheaterContract

class TheaterPresenter(
    view: TheaterContract.View,
    private val movie: Movie,
    private val theaterRepository: TheaterRepository,
) : TheaterContract.Presenter(view) {
    private val theaters: MutableList<DomainTheater> = mutableListOf()

    override fun loadTheaterList() {
        val newTheaters = theaterRepository.getAllByMovieId(movie.id)
        theaters.addAll(newTheaters)
        view.showTheaterList(newTheaters.map { it.toPresentation() })
    }

    override fun onTheaterClick(item: PresentationTheater) {
        view.showTicketingScreen(movie, item)
    }
}
