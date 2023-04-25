package woowacourse.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.activity.ReservationResultActivity
import woowacourse.movie.view.adapter.ReservationAdapter
import woowacourse.movie.view.data.ReservationViewDatas
import woowacourse.movie.view.getSerializable

class ReservationListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments ?: return null
        val reservations =
            arguments?.getSerializable<ReservationViewDatas>(ReservationViewDatas.RESERVATION_VIEW_DATAS_EXTRA_NAME)
                ?: return null
        val view = inflater.inflate(R.layout.fragment_reservation_list, container, false)
        makeReservationRecyclerView(view, reservations)
        return view
    }

    private fun makeReservationRecyclerView(view: View, reservations: ReservationViewDatas) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.reservation_list_recycler)
        recyclerView.adapter = ReservationAdapter(reservations) {
            startActivity(
                ReservationResultActivity.from(
                    view.context,
                    it.movie,
                    it.reservationDetail,
                    it.seats,
                    it.price
                )
            )
        }
        val decoration = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
    }

    companion object {
        fun from(reservationViewDatas: ReservationViewDatas) = Bundle().apply {
            putSerializable(
                ReservationViewDatas.RESERVATION_VIEW_DATAS_EXTRA_NAME, reservationViewDatas
            )
        }
    }
}
