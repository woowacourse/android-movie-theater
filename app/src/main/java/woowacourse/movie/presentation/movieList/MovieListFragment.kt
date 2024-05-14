package woowacourse.movie.presentation.movieList

import android.os.Bundle
import android.view.View
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.presentation.base.BindingFragment
import woowacourse.movie.presentation.movieList.cinemaListDialog.ChooseCinemasBottomSheetFragment
import woowacourse.movie.presentation.movieList.model.MovieDisplay

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
