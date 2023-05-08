package woowacourse.movie.ui.moviedetail

import woowacourse.movie.domain.PeopleCount
import woowacourse.movie.mapper.toModel
import woowacourse.movie.uimodel.PeopleCountModel
import woowacourse.movie.uimodel.TheaterModel
import java.time.LocalDate
import java.time.LocalTime

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val theater: TheaterModel,
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

    override fun setDateSpinner() {
        view.setDateSpinner(theater.times.keys.toList())
    }

    override fun setTimeSpinner(date: LocalDate) {
        updateTimesByDate(date)
        view.setTimeSpinner()
    }

    override fun updatePeopleCount(count: Int) {
        peopleCount = PeopleCount(count)
    }

    override fun updateTimesByDate(date: LocalDate) {
        _times.clear()
        _times.addAll(theater.times[date] ?: throw IllegalArgumentException())
    }
}
