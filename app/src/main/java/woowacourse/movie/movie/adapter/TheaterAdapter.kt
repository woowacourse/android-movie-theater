package woowacourse.movie.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.movie.TheaterUiModel

class TheaterAdapter(
    private val theaters: List<TheaterUiModel>,
    private val onSelectClick: (TheaterUiModel) -> Unit,
) : RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.theater_item, parent, false)
        return TheaterViewHolder(view)
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        val theater = theaters[position]
        val context = holder.itemView.context

        holder.place.text = context.getString(R.string.theater_place, theater.place)
        holder.count.text = context.getString(R.string.theater_movie_count, theater.schedules.map { it.screeningTimes }.size)
        holder.button.setOnClickListener {
            onSelectClick(theater)
        }
    }

    inner class TheaterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val place: TextView = view.findViewById(R.id.tv_theater_place)
        val count: TextView = view.findViewById(R.id.tv_movie_count)
        val button: TextView = view.findViewById(R.id.btn_select_theater)
    }
}
