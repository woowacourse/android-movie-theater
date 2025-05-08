package woowacourse.movie.reservation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.ui.model.TicketUiModel

class ReservationListAdapter(
    private val movieReservationList: List<TicketUiModel>,
    private val onReservationClick: ReservationClickListener,
) : RecyclerView.Adapter<ReservationListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationListViewHolder {
        return ReservationListViewHolder.from(parent, onReservationClick)
    }

    override fun onBindViewHolder(
        holder: ReservationListViewHolder,
        position: Int,
    ) {
        holder.bind(movieReservationList[position])
    }

    override fun getItemCount(): Int = movieReservationList.size
}
