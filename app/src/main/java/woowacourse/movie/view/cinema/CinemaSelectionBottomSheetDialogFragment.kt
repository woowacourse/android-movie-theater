package woowacourse.movie.view.cinema

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.contract.cinema.CinemaSelectionContract
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.presenter.cinema.CinemaSelectionPresenter
import woowacourse.movie.view.cinema.adapter.CinemaAdapter
import woowacourse.movie.view.reservation.ReservationActivity

class CinemaSelectionBottomSheetDialogFragment :
    BottomSheetDialogFragment(),
    CinemaSelectionContract.View {
    private lateinit var cinemasView: RecyclerView
    private var cinemaAdapter: CinemaAdapter? = null
    private val presenter = CinemaSelectionPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cinemaAdapter =
            CinemaAdapter(
                screening = arguments.screening ?: error(""),
                onClickItem =
                    { presenter.onSelectCinema(arguments.screening ?: error("")) },
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? =
        inflater.inflate(
            R.layout.fragment_cinema_selection_bottom_sheet_dialog,
            container,
            false,
        )

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        cinemasView = view.findViewById(R.id.recycler_view_cinema_selection)
        cinemasView.adapter = cinemaAdapter
        presenter.presentCinemas()
    }

    override fun setCinemas(cinemas: List<Cinema>) {
        cinemaAdapter?.submitList(cinemas) ?: error("")
    }

    override fun navigateToReservationScreen(screening: Screening) {
        val intent = ReservationActivity.newIntent(requireContext(), screening)
        startActivity(intent)
    }

    @Suppress("DEPRECATION")
    private val Bundle?.screening: Screening?
        get() {
            if (this == null) return null

            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
                    getSerializable(ARGUMENT_SCREENING, Screening::class.java)

                else -> getSerializable(ARGUMENT_SCREENING) as? Screening
            }
        }

    companion object {
        fun newInstance(screening: Screening): CinemaSelectionBottomSheetDialogFragment {
            val args =
                Bundle().apply {
                    putSerializable(ARGUMENT_SCREENING, screening)
                }

            val fragment = CinemaSelectionBottomSheetDialogFragment()
            fragment.arguments = args
            return fragment
        }

        private const val ARGUMENT_SCREENING = "ARGUMENT_SCREENING"
    }
}
