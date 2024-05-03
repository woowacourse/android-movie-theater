package woowacourse.movie.view.theater

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterSelectionBinding
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.presenter.theater.TheaterSelectionContract
import woowacourse.movie.presenter.theater.TheaterSelectionPresenter
import woowacourse.movie.view.home.HomeFragment.Companion.MOVIE_ID
import woowacourse.movie.view.reservation.ReservationDetailActivity
import woowacourse.movie.view.theater.adapter.TheaterSelectionAdapter

class TheaterSelectionFragment : BottomSheetDialogFragment(), TheaterSelectionContract.View {
    private var _binding: FragmentTheaterSelectionBinding? = null
    private val binding: FragmentTheaterSelectionBinding get() = _binding!!
    private var movieId: Int = 0
    private lateinit var presenter: TheaterSelectionPresenter
    private lateinit var theaterSelectionAdapter: TheaterSelectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater_selection, container, false)
        binding.theaterSelection = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieId = receiveMovieId()
        presenter =
            TheaterSelectionPresenter(
                view = this@TheaterSelectionFragment,
                movieId = movieId,
            )
        initTheaterRecyclerView()
        loadTheaters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("ResourceType")
    override fun navigateToDetail(
        movieId: Int,
        theaterId: Int,
    ) {
        Intent(context, ReservationDetailActivity::class.java).apply {
            putExtra(MOVIE_ID, movieId)
            putExtra(THEATER_ID, theaterId)
            startActivity(this)
        }
    }

    private fun receiveMovieId(): Int {
        return when (val id = arguments?.getInt(MOVIE_ID)) {
            null -> {
                dismissNow()
                -1
            }

            else -> id
        }
    }

    private fun initTheaterRecyclerView() {
        theaterSelectionAdapter =
            TheaterSelectionAdapter { theaterId ->
                presenter.loadTheater(theaterId)
            }
        binding.recyclerViewTheaterSelection.apply {
            adapter = theaterSelectionAdapter
        }
    }

    private fun loadTheaters() {
        theaterSelectionAdapter.updateTheaters(TheaterDao().findTheaterByMovieId(movieId))
    }

    companion object {
        const val THEATER_ID = "theaterId"
    }
}
