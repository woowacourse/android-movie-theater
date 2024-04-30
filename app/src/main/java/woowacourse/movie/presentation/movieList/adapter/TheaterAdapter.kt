package woowacourse.movie.presentation.movieList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.Theater

class TheaterAdapter(private val theaters: List<Theater>) :
    RecyclerView.Adapter<TheaterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_theater, parent, false)
        return TheaterViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return theaters.size
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(theaters[position])
    }
}

class TheaterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.item_theater_name_tv)
    private val time: TextView = view.findViewById(R.id.item_theater_time_tv)

    fun bind(theater: Theater) {
        name.text = theater.name
        time.text = theater.screens.size.toString()
    }
}
