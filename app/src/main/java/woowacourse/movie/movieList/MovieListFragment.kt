package woowacourse.movie.movieList

import MovieAdapter
import MovieListView
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.common.BindingFragment
import woowacourse.movie.common.ui.withArgs
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.model.MovieDisplayData
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.movieList.cinemaListDialog.TheatersBottomSheetFragment

class MovieListFragment : BindingFragment<FragmentMovieListBinding>(R.layout.fragment_movie_list),
    MovieListView {

    private var _presenter: MovieListPresenter? = null
    private val presenter get() = _presenter ?: error(getString(R.string.null_point_error))
    private lateinit var adapter: MovieAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initPresenter()
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun updateAdapter(displayData: List<MovieDisplayData>) {
        adapter.updateItems(displayData)
    }

    override fun navigateToCinemaView(theater: Theater) {
        val fragment =
            (
                    childFragmentManager.findFragmentByTag(TheatersBottomSheetFragment.TAG)
                            as? TheatersBottomSheetFragment
                    ) ?: TheatersBottomSheetFragment()
        val bottomSheet =
            fragment.withArgs {
                putSerializable(THEATER_KEY, theater)
            }
        bottomSheet.show(childFragmentManager, TheatersBottomSheetFragment.TAG)
    }

    private fun initViews() {
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        adapter =
            MovieAdapter(requireContext(), mutableListOf()) { position ->
                presenter.onDetailButtonClicked(position)
            }
        binding.rvMovies.adapter = adapter
    }

    private fun initPresenter() {
        _presenter = MovieListPresenter(this)
    }

    companion object {
        const val THEATER_KEY = "THEATER_KEY"
    }
}
