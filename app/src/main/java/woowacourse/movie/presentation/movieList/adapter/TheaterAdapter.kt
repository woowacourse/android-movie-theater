package woowacourse.movie.presentation.movieList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.Theater
import woowacourse.movie.presentation.movieList.viewholder.TheaterViewHolder

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
