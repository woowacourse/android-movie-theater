package woowacourse.movie.presentation.ticket.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTicketBinding
import woowacourse.movie.domain.model.Ticket

class TicketAdapter(
    private val items: List<Ticket>,
    private val onTicketClicked: (Ticket) -> Unit,
) : RecyclerView.Adapter<TicketViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TicketViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding, onTicketClicked)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: TicketViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
