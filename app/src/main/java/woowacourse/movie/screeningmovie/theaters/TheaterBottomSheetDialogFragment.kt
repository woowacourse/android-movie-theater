package woowacourse.movie.screeningmovie.theaters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.data.DummyMovies
import woowacourse.movie.databinding.BottomSheetTheaterBinding
import woowacourse.movie.moviereservation.MovieReservationActivity
import woowacourse.movie.screeningmovie.AdapterClickListener

class TheaterBottomSheetDialogFragment :
    BottomSheetDialogFragment(),
    AdapterClickListener,
    TheaterContract.View {
    private lateinit var presenter: TheaterContract.Presenter
    private var movieId: Long = -1L

    private lateinit var binding: BottomSheetTheaterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetTheaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        movieId = this.arguments?.getLong(EXTRA_SCREENING_MOVIE_ID) ?: error("movie id가 잘못 들어옴")
        presenter = TheaterPresenter(DummyMovies, this)
        presenter.loadTheaters(movieId)
    }

    override fun onClick(theaterId: Long) {
        presenter.selectTheater(movieId, theaterId)
    }

    override fun showTheaters(theaterUiModels: List<TheaterUiModel>) {
        binding.rcvTheater.adapter =
            TheaterAdapter(
                theaterUiModels,
                this,
            )
    }

    override fun navigateMovieReservation(
        screeningMovieId: Long,
        theaterId: Long,
    ) {
        startActivity(
            MovieReservationActivity.getIntent(
                requireContext(),
                screeningMovieId,
                theaterId,
            ),
        )
    }

    companion object {
        private const val EXTRA_SCREENING_MOVIE_ID = "screeningMovieId"

        fun getBundle(movieId: Long): Bundle {
            return Bundle().apply { this.putLong(EXTRA_SCREENING_MOVIE_ID, movieId) }
        }
    }
}
