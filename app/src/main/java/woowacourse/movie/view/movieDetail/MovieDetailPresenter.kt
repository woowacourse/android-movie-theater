package woowacourse.movie.view.movieDetail

import woowacourse.movie.domain.PeopleCount
import woowacourse.movie.model.MovieModel
import woowacourse.movie.model.mapToMovie
import java.time.LocalTime

class MovieDetailPresenter(private val view: MovieDetailContract.View) :
    MovieDetailContract.Presenter {
    private var peopleCount = PeopleCount()

    override fun setPeopleCount(count: Int) {
        peopleCount = PeopleCount(count)
        view.setPeopleCount(count)
    }

    override fun plusPeopleCount() {
        peopleCount = peopleCount.plusCount()
        view.setPeopleCount(peopleCount.value)
    }

    override fun minusPeopleCount() {
        peopleCount = peopleCount.minusCount()
        view.setPeopleCount(peopleCount.value)
    }

    override fun setScreeningDates(movie: MovieModel) {
        view.setDateSpinner(movie.mapToMovie().getDatesBetweenTwoDates())
    }

    override fun setScreeningTimes(screeningTimes: List<LocalTime>) {
        view.setTimeSpinner(screeningTimes)
    }
}
