package woowacourse.movie.ui.moviedetail

import woowacourse.movie.domain.PeopleCount
import woowacourse.movie.domain.TimesGenerator
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toModel
import woowacourse.movie.model.MovieListModel
import woowacourse.movie.model.PeopleCountModel
import java.time.LocalDate
import java.time.LocalTime

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
) : MovieDetailContract.Presenter {

    private var peopleCount = PeopleCount()
    val peopleCountModel: PeopleCountModel
        get() = peopleCount.toModel()

    private val _times = mutableListOf<LocalTime>()
    val times: List<LocalTime>
        get() = _times.toList()

    override fun addCount() {
        peopleCount = peopleCount.plusCount()
        view.setPeopleCountView(peopleCount.count)
    }

    override fun minusCount() {
        peopleCount = peopleCount.minusCount()
        view.setPeopleCountView(peopleCount.count)
    }

    override fun updatePeopleCount(count: Int) {
        peopleCount = PeopleCount(count)
    }

    override fun updateTimesByDate(date: LocalDate) {
        _times.clear()
        _times.addAll(TimesGenerator.getTimesByDate(date))
    }

    override fun getDatesBetweenTwoDates(movie: MovieListModel.MovieModel): List<LocalDate> {
        return movie.toDomain().getDatesBetweenTwoDates()
    }
}
