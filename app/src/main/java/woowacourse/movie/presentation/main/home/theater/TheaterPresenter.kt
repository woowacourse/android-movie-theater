package woowacourse.movie.presentation.main.home.theater

class TheaterPresenter(private val view: TheaterContract.View) :
    TheaterContract.Presenter {
    override fun itemClicked(movieId: Long, theaterId: Long) {
        view.navigateToDetailActivity(movieId, theaterId)
    }
}
