package woowacourse.movie.presentation.bookedticketlist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.R
import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.presentation.model.TicketModel
import woowacourse.movie.presentation.util.formatDotDateTimeColonSeparateBar

class BookedTicketViewHolder(
    view: View,
    clickListener: (TicketModel) -> Unit,
    getPositionData: (Int) -> TicketModel
) : ViewHolder(view) {

    private val textBookedTicketsDateTime: TextView =
        view.findViewById(R.id.textBookedTicketsDateTime)
    private val textBookedTicketsTitle: TextView = view.findViewById(R.id.textBookedTicketsTitle)

    init {
        setItemOnClickListener(view, clickListener, getPositionData)
    }

    fun bind(ticketModel: TicketModel, getMovieDataById: (Long) -> Movie) {
        textBookedTicketsDateTime.text =
            ticketModel.bookedDateTime.formatDotDateTimeColonSeparateBar()
        textBookedTicketsTitle.text = getMovieDataById(ticketModel.movieId).title
    }

    private fun setItemOnClickListener(
        view: View,
        clickListener: (TicketModel) -> Unit,
        getPositionData: (Int) -> TicketModel
    ) {
        view.setOnClickListener {
            clickListener(getPositionData(adapterPosition))
        }
    }
}
