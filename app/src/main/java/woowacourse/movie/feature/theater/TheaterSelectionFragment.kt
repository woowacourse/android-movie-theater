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

class TheaterSelectionFragment : BottomSheetDialogFragment(), TheaterSelectionContract.View {
    private var _binding: FragmentTheaterSelectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: TheaterSelectionPresenter
    private var movieId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater_selection, container, false)
        movieId = receiveMovieId()
        initPresenter()
        initTheaterRecyclerView()
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun navigateToDetail(
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
                movieId,
            )
    }

    private fun initTheaterRecyclerView() {
        val theaterSelectionAdapter =
            TheaterSelectionAdapter(
                TheaterDao().findTheaterByMovieId(receiveMovieId()),
            ) { theaterId ->
                presenter.sendTheaterInfoToDetail(theaterId)
            }
        binding.recyclerViewTheaterSelection.apply {
            adapter = theaterSelectionAdapter
        }
    }

    companion object {
        const val THEATER_ID = "theaterId"
        private const val DEFAULT_MOVIE_ID = -1
    }
}
