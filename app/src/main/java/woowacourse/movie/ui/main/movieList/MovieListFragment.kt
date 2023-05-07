package woowacourse.movie.ui.main.movieList

import android.content.Context
import android.os.Bundle
import android.view.View
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.BaseFragment
import woowacourse.movie.ui.adapter.MovieListAdapter
import woowacourse.movie.ui.adv.AdvDetailActivity
import woowacourse.movie.ui.itemModel.AdvItemModel
import woowacourse.movie.ui.itemModel.MovieItemModel
import woowacourse.movie.ui.main.cinemaBottomSheet.CinemaListBottomSheet

class MovieListFragment : BaseFragment(), MovieListContract.View {
    override lateinit var presenter: MovieListContract.Presenter
    override lateinit var binding: FragmentMovieListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setUpMovieList()
    }

    override fun initBinding() {
        binding = FragmentMovieListBinding.inflate(layoutInflater)
    }

    override fun initPresenter() {
        presenter = MovieListPresenter(this)
    }

    override fun setMovieList(movieList: List<MovieState>, advList: List<AdvState>) {
        binding.movieList.adapter = MovieListAdapter(
            movie = movieList.map(::MovieItemModel),
            adv = advList.map(::AdvItemModel),
            onClickMovie = { movieItemModel -> showCinemaBottomSheet(movieItemModel.movieState) },
            onClickAdv = { advItemModel -> navigateAdbDetail(advItemModel.advState) }
        )
    }

    private fun showCinemaBottomSheet(movie: MovieState) {
        CinemaListBottomSheet.newInstance(movie)
            .show(parentFragmentManager, CinemaListBottomSheet.TAG_CINEMA_LIST_BOTTOM_SHEET)
    }

    private fun navigateAdbDetail(adbState: AdvState) {
        AdvDetailActivity.startActivity(activity as Context, adbState)
    }
}
