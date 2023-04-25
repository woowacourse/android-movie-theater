package woowacourse.movie.view.moviemain.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.model.ReservationUiModel
import java.time.LocalDateTime

class ReservationListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = ReservationListAdapter(
            listOf(
                ReservationUiModel(
                    "해리포터1",
                    LocalDateTime.of(2023, 1, 1, 20, 0),
                    2,
                    listOf("A1", "B2"),
                    10000,
                ),
                ReservationUiModel(
                    "해리포터2",
                    LocalDateTime.of(2023, 2, 1, 20, 0),
                    2,
                    listOf("A1", "B2"),
                    20000,
                ),
            ),
        )
    }
}
