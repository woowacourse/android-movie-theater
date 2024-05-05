package woowacourse.movie.list.presenter

import woowacourse.movie.list.contract.TheaterContract

class TheaterPresenter(private val view: TheaterContract.View) :
    TheaterContract.Presenter {
    override fun itemClicked(movieId: Long, theaterId: Long) {
        view.navigateToDetailActivity(movieId, theaterId)
    }
}
