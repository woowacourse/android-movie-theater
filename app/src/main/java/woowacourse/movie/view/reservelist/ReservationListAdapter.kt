package woowacourse.movie.view.reservelist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.domain.model.Ticket

class ReservationListAdapter(
    val items: List<Ticket>,
    val onItemClick: (Ticket) -> Unit,
) : RecyclerView.Adapter<ReservationListViewHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ReservationListViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationListViewHolder {
        return ReservationListViewHolder(parent, onItemClick)
    }
}
