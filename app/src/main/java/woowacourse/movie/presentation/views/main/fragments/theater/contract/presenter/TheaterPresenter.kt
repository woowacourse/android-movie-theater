package woowacourse.movie.presentation.views.main.fragments.theater.contract.presenter

import com.woowacourse.data.repository.theater.TheaterRepository
import woowacourse.movie.domain.model.theater.DomainTheater
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.model.theater.PresentationTheater
import woowacourse.movie.presentation.views.main.fragments.theater.contract.TheaterContract

class TheaterPresenter(
    private val movie: Movie,
    private val theaterRepository: TheaterRepository,
) : TheaterContract.Presenter() {
    private val theaters: MutableList<DomainTheater> = mutableListOf()

    override fun attach(view: TheaterContract.View) {
        super.attach(view)
        loadTheaterList()
    }

    override fun loadTheaterList() {
        val newTheaters = theaterRepository.getAllByMovieId(movie.id)
        theaters.addAll(newTheaters)
        requireView().showTheaterList(newTheaters.map { it.toPresentation() })
    }

    override fun onTheaterClick(item: PresentationTheater) {
        requireView().showTicketingScreen(movie, item)
    }
}
