package woowacourse.movie.view.reservelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.domain.model.Ticket

class ReservationListAdapter(
    val items: List<Ticket>,
    val onItemClick: (Ticket) -> Unit,
) : RecyclerView.Adapter<ViewHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(items[position], onItemClick)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(ItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}

class ViewHolder(val binding: ItemReservationBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        data: Ticket,
        onItemClick: (Ticket) -> Unit,
    ) {
        binding.ticket = data
        binding.root.setOnClickListener {
            onItemClick(data)
        }
    }
}
