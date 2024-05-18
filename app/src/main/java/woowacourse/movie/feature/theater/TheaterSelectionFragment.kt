package woowacourse.movie.feature.theater

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var theaterSelectionAdapter: TheaterSelectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater_selection, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        initTheaterSelectionAdapter()
        presenter.loadTheater()
    }

    override fun showTheaters(
        theaters: List<Theater>,
        screeningCounts: List<Int>,
    ) {
        theaterSelectionAdapter.updateData(theaters, screeningCounts)
    }

    @SuppressLint("ResourceType")
    override fun navigateToReservation(
        movieId: Int,
        theaterId: Int,
    ) {
        val intent = Intent(context, ReservationActivity::class.java)
        intent.putExtra(MOVIE_ID, movieId)
        intent.putExtra(THEATER_ID, theaterId)
        startActivity(intent)
    }

    override fun showErrorSnackBar() {
        val snackBar =
            Snackbar.make(
                requireView(),
                getString(R.string.all_error),
                Snackbar.LENGTH_INDEFINITE,
            )
        snackBar.setAction(R.string.all_confirm) {
            snackBar.dismiss()
            dismissNow()
        }
        snackBar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun receiveMovieId(): Int? = arguments?.getInt(MOVIE_ID)

    private fun initPresenter() {
        presenter =
            TheaterSelectionPresenter(
                view = this@TheaterSelectionFragment,
                TheaterDao(),
                receiveMovieId(),
            )
    }

    private fun initTheaterSelectionAdapter() {
        theaterSelectionAdapter =
            TheaterSelectionAdapter { theaterId ->
                presenter.sendTheaterInfoToReservation(theaterId)
            }
        binding.rvTheaterSelection.adapter = theaterSelectionAdapter
    }

    companion object {
        const val THEATER_ID = "theaterId"
    }
}
