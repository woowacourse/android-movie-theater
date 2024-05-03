package woowacourse.movie.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.DummyMovieRepository
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.movielist.theaters.TheaterBottomSheetDialogFragment
import woowacourse.movie.movielist.uimodel.ListItemUiModel

class MovieListFragment : Fragment(), MovieListContract.View, AdapterClickListener {
    private lateinit var presenter: MovieListPresenter
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MovieListPresenter(this, DummyMovieRepository)
        presenter.loadScreeningMovies()
    }

    override fun showMovies(movies: List<ListItemUiModel>) {
        val listView = binding.rcvScreeningMovie
        listView.adapter =
            MovieAdapter(movies, this)
    }

    override fun showTheaters(screeningMovieId: Long) {
        val fragment = TheaterBottomSheetDialogFragment()
        val bundle = TheaterBottomSheetDialogFragment.getBundle(screeningMovieId)
        fragment.arguments = bundle
        fragment.show(parentFragmentManager, "theaterBottomSheet")
    }

    override fun onClick(id: Long) {
        presenter.startReservation(id)
    }
}
