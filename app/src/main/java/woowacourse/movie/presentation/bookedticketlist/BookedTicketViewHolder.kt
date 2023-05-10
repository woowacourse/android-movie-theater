package woowacourse.movie.presentation.bookedticketlist

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.BookedTicketItemBinding
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.TicketModel
import woowacourse.movie.presentation.util.formatDotDateTimeColonSeparateBar

class BookedTicketViewHolder(
    private val binding: BookedTicketItemBinding,
    getMovieModel: (TicketModel) -> MovieModel,
    clickListener: (TicketModel) -> Unit,
) : ViewHolder(binding.root) {

    private lateinit var ticketModel: TicketModel
    private lateinit var getMovieModel: (TicketModel) -> MovieModel

    init {
        setItemOnClickListener(clickListener)
        initMovieModelGetter(getMovieModel)
    }

    fun bind(ticketModel: TicketModel) {
        this.ticketModel = ticketModel
        binding.textBookedTicketsDateTime.text =
            ticketModel.bookedDateTime.formatDotDateTimeColonSeparateBar()
        binding.textBookedTicketsTitle.text = getMovieModel(ticketModel).title
    }

    private fun setItemOnClickListener(
        clickListener: (TicketModel) -> Unit,
    ) {
        binding.root.setOnClickListener {
            clickListener(ticketModel)
        }
    }

    private fun initMovieModelGetter(
        getMovieModel: (TicketModel) -> MovieModel,
    ) {
        this.getMovieModel = getMovieModel
    }
}
