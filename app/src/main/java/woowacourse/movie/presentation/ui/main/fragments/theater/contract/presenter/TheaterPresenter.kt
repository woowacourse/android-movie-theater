package woowacourse.movie.presentation.ui.main.fragments.theater.contract.presenter

import woowacourse.movie.domain.model.repository.TheaterRepository
import woowacourse.movie.domain.model.theater.DomainTheater
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.ui.main.fragments.theater.contract.TheaterContract

class TheaterPresenter(
    view: TheaterContract.View,
    private val movie: Movie,
    private val theaterRepository: TheaterRepository,
) : TheaterContract.Presenter(view) {
    private val theaters: MutableList<DomainTheater> = mutableListOf()

    override fun loadTheaterList() {
        val newTheaters = theaterRepository.getAllByMovieId(movie.id)
        theaters.clear()
        theaters.addAll(newTheaters)

        view.showTheaterList(newTheaters.map { it.toPresentation() })
    }

    override fun handleItem(item: ListItem) {
        view.showTicketingScreen(movie, item)
    }
}
