package woowacourse.movie.view.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.movies.viewholder.TheaterViewHolder

class TheaterAdapter(
    private val items: Theaters,
    private val movieId: Int,
    private val onclick: (String) -> Unit,
) : RecyclerView.Adapter<TheaterViewHolder>() {
    override fun getItemCount(): Int = items.size()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.theater_item, parent, false)
        return TheaterViewHolder(view, onclick)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        val item = items[position]
        holder.bind(item, movieId)
    }
}
