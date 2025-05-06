package woowacourse.movie.view.movies.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.core.base.BaseViewHolder
import woowacourse.movie.view.movies.adapter.model.MovieRvItem

class MovieAdapter(
    private val handler: Handler,
    private val movieRvItems: List<MovieRvItem>,
) : RecyclerView.Adapter<BaseViewHolder>() {
    override fun getItemCount(): Int = movieRvItems.size

    override fun getItemViewType(position: Int): Int = movieRvItems[position].viewType.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder {
        return when (ViewType.entries[viewType]) {
            ViewType.VIEW_TYPE_ADVERTISEMENT ->
                AdvertiseViewHolder(
                    parent,
                    R.layout.advertisement_item,
                )

            ViewType.VIEW_TYPE_MOVIE ->
                MovieViewHolder(
                    parent,
                    R.layout.movie_item,
                    handler,
                )
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
    ) {
        when (holder) {
            is AdvertiseViewHolder -> holder.bind(movieRvItems[position] as MovieRvItem.AdItem)
            is MovieViewHolder -> holder.bind(movieRvItems[position] as MovieRvItem.MovieItem)
        }
    }

    enum class ViewType {
        VIEW_TYPE_ADVERTISEMENT,
        VIEW_TYPE_MOVIE,
    }

    interface Handler : MovieViewHolder.Handler
}
