package woowacourse.movie.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.Theater

class TheaterAdapter(
    private val items: List<Theater>,
) : RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_theater, parent, false)
        return TheaterViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    class TheaterViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.textview_theater_name)

        fun bind(item: Theater) {
            name.text = item.name
        }
    }
}
