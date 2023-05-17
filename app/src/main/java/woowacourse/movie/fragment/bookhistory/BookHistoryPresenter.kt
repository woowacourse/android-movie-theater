package woowacourse.movie.fragment.bookhistory

import woowacourse.movie.data.MovieRepository

class BookHistoryPresenter(
    val view: BookHistoryContract.View,
    private val repository: MovieRepository,
) : BookHistoryContract.Presenter {

    override fun setMovieRecyclerView() {
        val bookedData = repository.loadData()
        view.setMovieRecyclerView(bookedData)
    }
}
