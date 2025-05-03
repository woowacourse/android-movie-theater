package woowacourse.movie.view.home.movies.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.view.base.BaseViewHolder
import woowacourse.movie.view.home.movies.model.MovieRvItem
import woowacourse.movie.view.home.movies.viewholder.AdvertiseViewHolder
import woowacourse.movie.view.home.movies.viewholder.MovieViewHolder

class MovieAdapter(
    private val handler: Handler,
    private val movieRvItems: List<MovieRvItem>,
) : RecyclerView.Adapter<BaseViewHolder<MovieRvItem>>() {
    override fun getItemCount(): Int = movieRvItems.size

    override fun getItemViewType(position: Int): Int = movieRvItems[position].viewType.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<MovieRvItem> {
        return when (val type = MovieRvItem.ViewType.entries[viewType]) {
            MovieRvItem.ViewType.VIEW_TYPE_ADVERTISEMENT ->
                AdvertiseViewHolder(
                    parent,
                    type.layoutRes,
                )

            MovieRvItem.ViewType.VIEW_TYPE_MOVIE ->
                MovieViewHolder(
                    parent,
                    type.layoutRes,
                    handler,
                )
        } as BaseViewHolder<MovieRvItem>
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<MovieRvItem>,
        position: Int,
    ) {
        holder.bind(movieRvItems[position])
    }

    interface Handler : MovieViewHolder.Handler
}
