package woowacourse.movie.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.Theater

class TheaterAdapter(
    private val theaters: List<Pair<Theater, Int>>,
    private val clickListener: (Int) -> Unit,
) : RecyclerView.Adapter<TheaterViewHolder>() {
    private fun onItemClickListener(position: Int) {
        clickListener(theaters[position].first.id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.theater_item, parent, false)
        return TheaterViewHolder(view, ::onItemClickListener)
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        holder.bind(theaters.get(position))
    }
}
