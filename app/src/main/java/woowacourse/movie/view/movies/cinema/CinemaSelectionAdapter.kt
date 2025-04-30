package woowacourse.movie.view.movies.cinema

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.R
import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.view.movies.OnMovieEventListener

class CinemaSelectionAdapter(
    val items: List<Screening>,
    val eventListener: OnCinemaSelectionListener,
) : RecyclerView.Adapter<CinemaViewHolder>() {

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        holder.bind(items[position], eventListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return CinemaViewHolder(view)
    }

    override fun getItemCount(): Int = items.size
}

class CinemaViewHolder(val view: View) : ViewHolder(view) {
    private val tvCinemaName = view.findViewById<TextView>(R.id.tv_cinema)
    private val tvScreeningTime = view.findViewById<TextView>(R.id.tv_screening_time)

    fun bind(
        screening: Screening,
        eventListener: OnCinemaSelectionListener,
    ) {
        tvCinemaName.text = screening.cinema.name
        tvScreeningTime.text = screening.screeningTimes.size.toString()
        view.setOnClickListener {
            eventListener.onReserveButtonClick(screening)
        }
    }
}