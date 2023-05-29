package woowacourse.movie.ui.moviedetail

import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toModel
import woowacourse.movie.uimodel.PeopleCountModel
import woowacourse.movie.uimodel.TheaterModel
import java.time.LocalDate
import java.time.LocalTime

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val theater: TheaterModel,
) : MovieDetailContract.Presenter {

    private val _times = mutableListOf<LocalTime>()
    val times: List<LocalTime>
        get() = _times.toList()

    override fun addCount(peopleCount: PeopleCountModel) {
        val newCount = peopleCount.toDomain().plusCount()
        setPeopleCountText(newCount.toModel())
        view.updatePeopleCount(newCount.count)
    }

    override fun minusCount(peopleCount: PeopleCountModel) {
        val newCount = peopleCount.toDomain().minusCount()
        setPeopleCountText(newCount.toModel())
        view.updatePeopleCount(newCount.count)
    }

    override fun setPeopleCountText(peopleCount: PeopleCountModel) {
        view.setPeopleCountView(peopleCount.count)
    }

    override fun setDateSpinner() {
        view.setDateSpinner(theater.times.keys.toList())
    }

    override fun setTimeSpinner(date: LocalDate) {
        updateTimesByDate(date)
        view.setTimeSpinner()
    }

    override fun updateTimesByDate(date: LocalDate) {
        _times.clear()
        _times.addAll(theater.times[date] ?: throw IllegalArgumentException())
    }
}
