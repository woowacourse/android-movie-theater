package woowacourse.movie.presenter

import woowacourse.movie.contract.MovieReservationContract
import woowacourse.movie.data.LocalFormattedTime
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.domain.Count
import woowacourse.movie.domain.ReservationDetail
import woowacourse.movie.domain.movieTimePolicy.MovieTime
import woowacourse.movie.domain.movieTimePolicy.WeekdayMovieTimePolicy
import woowacourse.movie.domain.movieTimePolicy.WeekendMovieTimePolicy
import woowacourse.movie.mapper.ReservationDetailMapper.toView
import woowacourse.movie.system.StateContainer
import java.time.LocalDate
import java.time.LocalDateTime

class MovieReservationPresenter(
    override val view: MovieReservationContract.View,
    var peopleCount: Count = Count(PEOPLE_DEFAULT_COUNT)
) : MovieReservationContract.Presenter {
    override fun addPeopleCount(count: Int) {
        peopleCount += count
        view.setCounterText(peopleCount.value)
    }

    override fun minusPeopleCount(count: Int) {
        peopleCount -= count
        view.setCounterText(peopleCount.value)
    }

    override fun reserveMovie(date: LocalDateTime, movie: MovieViewData) {
        val reservationDetail = ReservationDetail(
            date, peopleCount.value
        ).toView()
        view.startReservationResultActivity(reservationDetail, movie)
    }

    override fun selectDate(date: LocalDate) {
        val times = MovieTime(
            listOf(WeekdayMovieTimePolicy, WeekendMovieTimePolicy)
        ).determine(date).map { LocalFormattedTime(it) }
        view.setTimeSpinner(times)
    }

    override fun save(outState: StateContainer) {
        outState.save(PEOPLE_COUNT_SAVE_KEY, peopleCount.value)
    }

    override fun load(savedInstanceState: StateContainer?) {
        peopleCount = if (savedInstanceState == null) peopleCount
        else Count(savedInstanceState.load(PEOPLE_COUNT_SAVE_KEY))
        view.setCounterText(peopleCount.value)
    }

    companion object {
        private const val PEOPLE_COUNT_SAVE_KEY = "peopleCount"
        private const val PEOPLE_DEFAULT_COUNT = 1
    }
}
