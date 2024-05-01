package woowacourse.movie.screeningmovie.theaters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.data.DummyMovies
import woowacourse.movie.moviereservation.MovieReservationActivity
import woowacourse.movie.screeningmovie.AdapterClickListener

class TheaterBottomSheetDialogFragment :
    BottomSheetDialogFragment(),
    AdapterClickListener,
    TheaterContract.View {
    private lateinit var rcv: RecyclerView
    private lateinit var presenter: TheaterContract.Presenter
    private var movieId: Long = -1L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.bottom_sheet_theater, container, false)
        rcv = view.findViewById(R.id.rcv_theater)
        return view
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
        rcv.adapter =
            TheaterAdapter(
                theaterUiModels,
                this,
            )
    }

    override fun navigateMovieReservation(screeningMovieId: Long, theaterId: Long) {
        startActivity(MovieReservationActivity.getIntent(requireContext(), screeningMovieId, theaterId))
    }

    companion object {
        private const val EXTRA_SCREENING_MOVIE_ID = "screeningMovieId"

        fun getBundle(movieId: Long): Bundle {
            return Bundle().apply { this.putLong(EXTRA_SCREENING_MOVIE_ID, movieId) }
        }
    }
}
