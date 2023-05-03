package woowacourse.movie.presenter.moviedetail

import woowacourse.movie.contract.moviedetail.MovieDetailContract
import woowacourse.movie.domain.PeopleCount
import woowacourse.movie.domain.TimesGenerator
import woowacourse.movie.ui.model.MovieModel
import woowacourse.movie.ui.model.mapToMovie
import java.time.LocalDate

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

    override fun setScreeningTimes(date: LocalDate) {
        view.setTimeSpinner(TimesGenerator.getTimesByDate(date))
    }
}
