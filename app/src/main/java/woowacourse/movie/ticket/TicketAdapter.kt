package woowacourse.movie.ticket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.ui.model.TicketUiModel

class TicketAdapter(
    private val reservations: List<TicketUiModel>,
    private val onTicketClick: TicketClickListener,
) : RecyclerView.Adapter<TicketViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TicketViewHolder {
        return TicketViewHolder(parent, onTicketClick)
    }

    override fun getItemCount(): Int = reservations.size

    override fun onBindViewHolder(
        holder: TicketViewHolder,
        position: Int,
    ) {
        val reservation = reservations[position]

        holder.bind(reservation)
    }
}
