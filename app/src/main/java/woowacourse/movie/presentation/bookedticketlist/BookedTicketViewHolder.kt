package woowacourse.movie.presentation.bookedticketlist

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.BookedTicketItemBinding
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.TicketModel
import woowacourse.movie.presentation.util.formatDotDateTimeColonSeparateBar

class BookedTicketViewHolder(
    private val binding: BookedTicketItemBinding,
    private val getMovieModel: (TicketModel) -> MovieModel,
    clickListener: (TicketModel) -> Unit,
    getPositionData: (Int) -> TicketModel,
) : ViewHolder(binding.root) {

    init {
        setItemOnClickListener(clickListener, getPositionData)
    }

    fun bind(ticketModel: TicketModel) {
        binding.textBookedTicketsDateTime.text =
            ticketModel.bookedDateTime.formatDotDateTimeColonSeparateBar()
        binding.textBookedTicketsTitle.text = getMovieModel(ticketModel).title
    }

    private fun setItemOnClickListener(
        clickListener: (TicketModel) -> Unit,
        getPositionData: (Int) -> TicketModel,
    ) {
        binding.root.setOnClickListener {
            clickListener(getPositionData(adapterPosition))
        }
    }
}
