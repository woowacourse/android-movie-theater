package woowacourse.movie.view.movies.cinema

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentCinemaSeclectionBinding
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.view.extension.getParcelableCompat
import woowacourse.movie.view.reservation.ReservationActivity

class CinemaSelectionFragment : BottomSheetDialogFragment() {
    private lateinit var screenings: List<Screening>
    private lateinit var binding: FragmentCinemaSeclectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_cinema_seclection,
                container,
                false,
            )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.lvCinema.adapter =
            CinemaSelectionAdapter(
                screenings,
                object : OnCinemaSelectionListener {
                    override fun onReserveButtonClick(screening: Screening) {
                        val intent =
                            ReservationActivity
                                .newIntent(
                                    requireContext(),
                                    screening,
                                )
                        requireActivity().startActivity(intent)
                    }
                },
            )
        binding.lvCinema.layoutManager = LinearLayoutManager(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments?.takeIf { it.containsKey(SCREENING_KEY) }
        screenings = bundle?.getParcelableCompat(SCREENING_KEY) ?: throw IllegalArgumentException()
    }

    companion object {
        private const val SCREENING_KEY = "SCREENING_KEY"

        fun newInstance(screenings: List<Screening>): CinemaSelectionFragment {
            val fragment = CinemaSelectionFragment()
            val args = Bundle()
            args.putParcelableArray(SCREENING_KEY, screenings.toTypedArray())
            fragment.arguments = args
            return fragment
        }
    }
}
