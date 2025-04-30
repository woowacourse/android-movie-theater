package woowacourse.movie.view.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.movies.viewholder.TheaterViewHolder

class TheaterAdapter(
    private val items: Theaters,
    private val movieId: Int,
    private val onclick: (Theater) -> Unit,
) : RecyclerView.Adapter<TheaterViewHolder>() {
    override fun getItemCount(): Int = items.size()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val inflater =
            LayoutInflater.from(parent.context)

        val theaterItemBinding = TheaterItemBinding.inflate(inflater, parent, false)
        return TheaterViewHolder(theaterItemBinding, onclick)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        val item = items[position]
        holder.bind(item, item.screeningTimeCount(movieId))
    }
}
