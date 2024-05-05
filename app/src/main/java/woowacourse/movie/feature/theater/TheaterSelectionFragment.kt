package woowacourse.movie.feature.theater

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.MainActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterSelectionBinding
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.feature.home.HomeFragment.Companion.MOVIE_ID
import woowacourse.movie.feature.reservation.ReservationActivity
import woowacourse.movie.feature.theater.adapter.TheaterSelectionAdapter
import woowacourse.movie.model.theater.Theater

class TheaterSelectionFragment : BottomSheetDialogFragment(), TheaterSelectionContract.View {
    private var _binding: FragmentTheaterSelectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: TheaterSelectionPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater_selection, container, false)
        initPresenter()
        presenter.loadTheater()
        return binding.root
    }

    override fun showTheaters(
        theaters: List<Theater>,
        screeningCounts: List<Int>,
    ) {
        val theaterSelectionAdapter =
            TheaterSelectionAdapter { theaterId ->
                presenter.sendTheaterInfoToReservation(theaterId)
            }
        binding.recyclerViewTheaterSelection.adapter = theaterSelectionAdapter
        theaterSelectionAdapter.updateData(theaters, screeningCounts)
    }

    @SuppressLint("ResourceType")
    override fun navigateToReservation(
        movieId: Int,
        theaterId: Int,
    ) {
        val intent = Intent(context as MainActivity, ReservationActivity::class.java)
        intent.putExtra(MOVIE_ID, movieId)
        intent.putExtra(THEATER_ID, theaterId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun receiveMovieId(): Int {
        return when (val id = arguments?.getInt(MOVIE_ID)) {
            null -> {
                dismissNow()
                DEFAULT_MOVIE_ID
            }
            else -> id
        }
    }

    private fun initPresenter() {
        presenter =
            TheaterSelectionPresenter(
                view = this@TheaterSelectionFragment,
                TheaterDao(),
                receiveMovieId(),
            )
    }

    companion object {
        const val THEATER_ID = "theaterId"
        private const val DEFAULT_MOVIE_ID = -1
    }
}
