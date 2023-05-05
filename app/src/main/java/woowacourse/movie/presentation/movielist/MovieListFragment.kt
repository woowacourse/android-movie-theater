package woowacourse.movie.presentation.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.data.remote.DummyMovieStorage
import woowacourse.movie.presentation.booking.BookingActivity

class MovieListFragment : Fragment(), MovieListContract.View {
    private val movieItemAdapter by lazy { MovieItemAdapter() { clickBook(it) } }
    override lateinit var presenter: MovieListContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMovieListPresenter()
        setMovieAdapter()
        initMovieItemAdapterData()
    }

    private fun initMovieListPresenter() {
        presenter = MovieListPresenter(this, DummyMovieStorage())
    }

    private fun initMovieItemAdapterData() {
        presenter.getMovieItemsWithAds()
    }

    private fun setMovieAdapter() {
        requireActivity().findViewById<RecyclerView>(R.id.recyclerMainMovie).adapter =
            movieItemAdapter
    }

    override fun updateMovieListView(movieItems: List<MovieItem>) {
        movieItemAdapter.updateMovieItems(movieItems)
    }

    private fun clickBook(movieId: Long) {
        startActivity(BookingActivity.getIntent(requireActivity(), movieId))
    }
}
