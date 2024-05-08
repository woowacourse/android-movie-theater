package woowacourse.movie.movieList

import android.os.Bundle
import android.view.View
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.base.BindingFragment
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.ui.MovieDisplay
import woowacourse.movie.movieList.cinemaListDialog.ChooseCinemasBottomSheetFragment

class MovieListFragment :
    BindingFragment<FragmentMovieListBinding>(R.layout.fragment_movie_list),
    MovieListView {
    private lateinit var presenter: MovieListPresenter
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

    override fun updateAdapter(displayData: List<MovieDisplay>) {
        adapter.updateItems(displayData)
    }

    override fun navigateToCinemaView(theater: Theater) {
        val bottomSheet = ChooseCinemasBottomSheetFragment.newInstance(theater)
        bottomSheet.show(childFragmentManager, ChooseCinemasBottomSheetFragment.TAG)
    }

    private fun initViews() {
        adapter =
            MovieAdapter(requireContext()) { position ->
                presenter.onDetailButtonClicked(position)
            }
        binding.rvMovies.adapter = adapter
    }

    private fun initPresenter() {
        presenter = MovieListPresenter(this)
    }

    companion object {
        const val THEATER_KEY = "THEATER_KEY"
    }
}
