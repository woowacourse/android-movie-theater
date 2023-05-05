package woowacourse.movie.presentation.bookedticketlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.BookedTicketItemBinding
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.TicketModel

class BookedTicketsAdapter(
    private val clickListener: (TicketModel) -> Unit,
    private val getMovieModel: (TicketModel) -> MovieModel,
) :
    ListAdapter<TicketModel, RecyclerView.ViewHolder>(ticketDiffUtil) {
    private lateinit var inflater: LayoutInflater
    private lateinit var bookedTicketBinding: BookedTicketItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        bookedTicketBinding = BookedTicketItemBinding.inflate(inflater)

        return BookedTicketViewHolder(bookedTicketBinding, getMovieModel, clickListener, ::getItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as BookedTicketViewHolder
        viewHolder.bind(getItem(position))
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
