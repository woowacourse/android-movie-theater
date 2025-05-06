package woowacourse.movie.ui.view.cinema

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentCinemaSelectionBottomSheetDialogBinding
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ShowtimePolicy
import woowacourse.movie.ui.contract.cinema.CinemaSelectionContract
import woowacourse.movie.ui.presenter.cinema.CinemaSelectionPresenter
import woowacourse.movie.ui.view.cinema.adapter.CinemaAdapter
import woowacourse.movie.ui.view.reservation.ReservationActivity
import woowacourse.movie.ui.view.util.ErrorMessage

class CinemaSelectionBottomSheetDialogFragment :
    BottomSheetDialogFragment(),
    CinemaSelectionContract.View {
    private var _binding: FragmentCinemaSelectionBottomSheetDialogBinding? = null
    private val binding
        get() =
            _binding ?: error(
                ErrorMessage("_binding").notProvided(),
            )

    private lateinit var cinemaAdapter: CinemaAdapter
    private lateinit var presenter: CinemaSelectionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        initCinemaAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            FragmentCinemaSelectionBottomSheetDialogBinding.inflate(inflater, container, false)
        binding.cinemaAdapter = cinemaAdapter
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.presentCinemas()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initPresenter() {
        val screening =
            arguments.screening ?: error(
                ErrorMessage("screening").notProvided(),
            )
        presenter = CinemaSelectionPresenter(this, screening)
    }

    private fun initCinemaAdapter() {
        cinemaAdapter =
            CinemaAdapter(
                onClickItem = { cinemaName: String, showtimePolicy: ShowtimePolicy ->
                    presenter.onSelectCinema(cinemaName, showtimePolicy)
                },
            )
    }

    override fun setCinemas(cinemas: List<Cinema>) {
        cinemaAdapter.submitList(cinemas)
    }

    override fun navigateToReservationScreen(
        screening: Screening,
        cinemaName: String,
        showtimePolicy: ShowtimePolicy,
    ) {
        val intent =
            ReservationActivity.newIntent(
                requireContext(),
                screening,
                cinemaName,
                showtimePolicy,
            )
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
            val arguments =
                Bundle().apply {
                    putSerializable(ARGUMENT_SCREENING, screening)
                }

            val fragment = CinemaSelectionBottomSheetDialogFragment()
            fragment.arguments = arguments
            return fragment
        }

        private const val ARGUMENT_SCREENING = "ARGUMENT_SCREENING"
    }
}
