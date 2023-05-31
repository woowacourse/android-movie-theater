package woowacourse.movie.feature.reservation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.TicketsState

class TicketListAdapter(
    private var ticketsStates: List<TicketsState> = listOf(),
    private val itemClickEvent: (ticketsState: TicketsState) -> Unit
) : RecyclerView.Adapter<TicketsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_reservation, parent, false)
        return TicketsViewHolder(binding, itemClickEvent)
    }

    override fun getItemCount(): Int = ticketsStates.size

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) {
        holder.bind(ticketsStates[position])
    }

    fun setItems(ticketsStates: List<TicketsState>) {
        this.ticketsStates = ticketsStates.toList()
        notifyDataSetChanged()
    }
}
