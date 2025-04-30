package woowacourse.movie.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.R
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Theater

class TheaterListAdapter(
    val items: List<Theater>,
    val movie: Movie,
    val onClicked: (Theater) -> Unit,
) : RecyclerView.Adapter<TheaterListAdapter.TheaterViewHolder>() {
    inner class TheaterViewHolder(view: View) : ViewHolder(view) {
        private val theaterName: TextView = view.findViewById(R.id.theater_name)
        private val sizeOfRunningTime: TextView = view.findViewById(R.id.therter_running_time_size)

        init {
            itemView.setOnClickListener {
                onClicked(items[bindingAdapterPosition])
            }
        }

        fun setItem(item: Theater) {
            theaterName.text = item.name
            sizeOfRunningTime.text =
                sizeOfRunningTime.context.getString(
                    R.string.bottom_sheet_timetable, item.movieTimeTable(movie).size,
                )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_item, parent, false)
        return TheaterViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        val item = items[position]
        holder.setItem(item)
    }
}
