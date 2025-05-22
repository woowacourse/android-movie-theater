package woowacourse.movie.view.reservation.history

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.domain.model.ReservationInfo

class ReservationHistoryAdapter(
    private val items: List<ReservationInfo>,
    private val onClickHistory: (ReservationInfo) -> Unit,
) : RecyclerView.Adapter<HistoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryViewHolder = HistoryViewHolder.create(parent, onClickHistory)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: HistoryViewHolder,
        position: Int,
    ) {
        val reservationInfo = items[position]
        holder.bind(reservationInfo)
    }
}
