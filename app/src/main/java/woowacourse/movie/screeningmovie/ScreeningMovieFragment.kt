package woowacourse.movie.screeningmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.DummyMovieRepository
import woowacourse.movie.databinding.FragmentScreeningMovieBinding
import woowacourse.movie.screeningmovie.theaters.TheaterBottomSheetDialogFragment
import woowacourse.movie.screeningmovie.uimodel.ListItemUiModel

class ScreeningMovieFragment : Fragment(), ScreeningMovieContract.View, AdapterClickListener {
    private lateinit var presenter: ScreenMoviePresenter
    private lateinit var binding: FragmentScreeningMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentScreeningMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ScreenMoviePresenter(this, DummyMovieRepository)
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
