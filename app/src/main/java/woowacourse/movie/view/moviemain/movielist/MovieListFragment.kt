package woowacourse.movie.view.moviemain.movielist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.MovieMockRepository
import woowacourse.movie.data.TheaterMockRepository
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.view.model.MovieListModel
import woowacourse.movie.view.model.MovieTheater
import woowacourse.movie.view.reservation.ReservationActivity

class MovieListFragment : Fragment(R.layout.fragment_movie_list), MovieListContract.View {

    override lateinit var presenter: MovieListContract.Presenter
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MovieListPresenter(this, MovieMockRepository, TheaterMockRepository)
        presenter.loadMovieList()
    }

    override fun showMovieList(dataList: List<MovieListModel>) {
        val movieAdapter = MovieListAdapter(
            dataList = dataList
        ) { item -> presenter.onItemClick(item) }

        binding.movieRecyclerview.adapter = movieAdapter
    }

    override fun openReservationActivity(
        item: MovieListModel.MovieUiModel,
        movieTheater: MovieTheater
    ) {
        val intent = ReservationActivity.newIntent(requireContext(), item, movieTheater)
        startActivity(intent)
    }

    override fun openAdPage(item: MovieListModel.MovieAdModel) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        startActivity(intent)
    }

    override fun openTheaterBottomSheet(
        theaters: List<MovieTheater>,
        movie: MovieListModel.MovieUiModel
    ) {
        val movieTheaterAdapter = MovieTheaterAdapter(
            theaters
        ) { item ->
            openReservationActivity(movie, item)
        }
        val movieTheaterDialog = MovieTheaterDialog(movieTheaterAdapter)
        movieTheaterDialog.show(parentFragmentManager, MOVIE_THEATER_DIALOG_TAG)
    }

    companion object {
        private const val MOVIE_THEATER_DIALOG_TAG = "MOVIE_THEATER_DIALOG_TAG"
    }
}
