package woowacourse.movie.presentation.bookedticketlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.model.TicketModel

class BookedTicketsAdapter(
    private val clickListener: (TicketModel) -> Unit,
    override val presenter: BookedTicketsContract.Presenter
) :
    ListAdapter<TicketModel, RecyclerView.ViewHolder>(ticketDiffUtil),
    BookedTicketsContract.Adapter {
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        return BookedTicketViewHolder(
            inflater.inflate(R.layout.booked_ticket_item, null),
            clickListener,
            ::getItem
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as BookedTicketViewHolder
        viewHolder.bind(getItem(position), presenter::getMovieById)
    }

    companion object {
        private val ticketDiffUtil = object : DiffUtil.ItemCallback<TicketModel>() {
            override fun areItemsTheSame(oldItem: TicketModel, newItem: TicketModel): Boolean =
                oldItem.bookedDateTime == newItem.bookedDateTime

            override fun areContentsTheSame(oldItem: TicketModel, newItem: TicketModel): Boolean =
                oldItem == newItem
        }
    }
}
