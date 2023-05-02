package woowacourse.movie.ui.activity.detail.presenter

import woowacourse.movie.domain.PeopleCount
import woowacourse.movie.domain.TimesGenerator
import woowacourse.movie.ui.activity.detail.MovieDetailContract
import woowacourse.movie.ui.model.Times
import java.time.LocalDate

class MovieDetailPresenter(private val view: MovieDetailContract.View) :
    MovieDetailContract.Presenter {
    private var peopleCount = PeopleCount()
    private lateinit var times: Times

    override fun setPeopleCount(count: Int) {
        peopleCount = PeopleCount(count)
        view.setPeopleCount(peopleCount.value)
    }

    override fun decreasePeopleCount() {
        peopleCount = peopleCount.minusCount()
        view.setPeopleCount(peopleCount.value)
    }

    override fun increasePeopleCount() {
        peopleCount = peopleCount.plusCount()
        view.setPeopleCount(peopleCount.value)
    }

    override fun initTimes(date: LocalDate) {
        times = Times(TimesGenerator.getTimesByDate(date))
        view.initTimeSpinner(times.items)
    }

    override fun updateTimes(date: LocalDate) {
        times.changeTimes(TimesGenerator.getTimesByDate(date))
        view.updateTimeSpinner()
    }
}
