package woowacourse.movie.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.ReservationsViewData
import woowacourse.movie.error.FragmentError.finishWithError
import woowacourse.movie.error.ViewError
import woowacourse.movie.view.activity.ReservationResultActivity
import woowacourse.movie.view.adapter.ReservationAdapter
import woowacourse.movie.view.getSerializableCompat

class ReservationListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments ?: return finishWithError(ViewError.MissingExtras(MISSING_ARGUMENTS))
        val reservations =
            arguments?.getSerializableCompat<ReservationsViewData>(ReservationsViewData.RESERVATIONS_VIEW_DATA_EXTRA_NAME)
                ?: return finishWithError(ViewError.MissingExtras(ReservationsViewData.RESERVATIONS_VIEW_DATA_EXTRA_NAME))

        val view = inflater.inflate(R.layout.fragment_reservation_list, container, false)
        makeReservationRecyclerView(view, reservations)
        return view
    }

    private fun makeReservationRecyclerView(view: View, reservations: ReservationsViewData) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.reservation_list_recycler)
        recyclerView.adapter = ReservationAdapter(reservations) {
            startActivity(ReservationResultActivity.from(view.context, it))
        }
        val decoration = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
    }

    companion object {
        private const val MISSING_ARGUMENTS = "arguments"
        fun from(reservationsViewData: ReservationsViewData) = Bundle().apply {
            putSerializable(
                ReservationsViewData.RESERVATIONS_VIEW_DATA_EXTRA_NAME, reservationsViewData
            )
        }
    }
}
