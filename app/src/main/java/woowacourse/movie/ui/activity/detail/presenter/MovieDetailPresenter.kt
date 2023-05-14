package woowacourse.movie.ui.activity.detail.presenter

import woowacourse.movie.domain.PeopleCount
import woowacourse.movie.ui.activity.detail.MovieDetailContract

class MovieDetailPresenter(private val view: MovieDetailContract.View) :
    MovieDetailContract.Presenter {
    private var peopleCount = PeopleCount()

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
}
