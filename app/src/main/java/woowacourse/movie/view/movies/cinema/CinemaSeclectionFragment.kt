package woowacourse.movie.view.movies.cinema

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.view.extension.getParcelableCompat
import woowacourse.movie.view.reservation.ReservationActivity

class CinemaSeclectionFragment : BottomSheetDialogFragment() {
    private lateinit var screenings: List<Screening>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_cinema_seclection, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val list = view.findViewById<RecyclerView>(R.id.lv_cinema)
        list.adapter =
            CinemaSelectionAdapter(
                screenings,
                object : OnCinemaSelectionListener {
                    override fun onReserveButtonClick(screening: Screening) {
                        context?.let {
                            ReservationActivity
                                .newIntent(
                                    it,
                                    screening,
                                ).let(
                                    requireActivity()::startActivity,
                                )
                        }
                    }
                },
            )
        list.layoutManager = LinearLayoutManager(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments?.takeIf { it.containsKey(SCREENING_KEY) }
        screenings = bundle?.getParcelableCompat(SCREENING_KEY) ?: throw IllegalArgumentException()
    }

    companion object {
        private const val SCREENING_KEY = "SCREENING_KEY"

        fun newInstance(screenings: List<Screening>): CinemaSeclectionFragment {
            val fragment = CinemaSeclectionFragment()
            val args = Bundle()
            args.putParcelableArray(SCREENING_KEY, screenings.toTypedArray())
            fragment.arguments = args
            return fragment
        }
    }
}
