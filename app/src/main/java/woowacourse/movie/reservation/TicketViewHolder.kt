package woowacourse.movie.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ReservationListItemBinding
import woowacourse.movie.ui.model.TicketUiModel

class TicketViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.reservation_list_item, parent, false),
    ) {
    private val binding = ReservationListItemBinding.bind(itemView)

    fun bind(ticket: TicketUiModel) {
        binding.ticket = ticket
    }
}
