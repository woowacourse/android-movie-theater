package woowacourse.movie.list.view

class TheaterPresenter(private val view: TheaterFragmentContract.View) : TheaterFragmentContract.Presenter {
    override fun itemClicked(movieId: Long, theaterId: Long) {
        view.navigateToDetailActivity(movieId, theaterId)
    }
}
