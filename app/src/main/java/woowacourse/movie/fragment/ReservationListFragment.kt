package woowacourse.movie.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.view.data.ReservationViewDatas

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
        return inflater.inflate(R.layout.fragment_reservation_list, container, false)
    }

    companion object {
        fun from(reservationViewDatas: ReservationViewDatas) = Bundle().apply {
            Log.d("DYDY", reservationViewDatas.value[0].movie.title)
            putSerializable("", reservationViewDatas)
        }
    }
}
