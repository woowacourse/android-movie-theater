package woowacourse.movie.movielist.theaters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.data.DummyEverythingRepository
import woowacourse.movie.databinding.BottomSheetTheaterBinding
import woowacourse.movie.moviedetail.MovieDetailActivity
import woowacourse.movie.movielist.AdapterClickListener

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
        presenter = TheaterPresenter(DummyEverythingRepository, this)
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

    override fun navigateToMovieDetail(screeningScheduleId: Long) {
        startActivity(
            MovieDetailActivity.getIntent(
                requireContext(),
                screeningScheduleId,
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
