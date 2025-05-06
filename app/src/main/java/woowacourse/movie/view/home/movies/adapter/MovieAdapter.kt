package woowacourse.movie.view.home.movies.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.home.model.FeedUiModel
import woowacourse.movie.view.home.model.FeedUiModel.AdUiModel
import woowacourse.movie.view.home.model.FeedUiModel.MovieUiModel
import woowacourse.movie.view.home.movies.MovieListEventHandler
import woowacourse.movie.view.home.movies.viewholder.AdViewHolder
import woowacourse.movie.view.home.movies.viewholder.MovieViewHolder

class MovieAdapter(
    private val itemsList: List<FeedUiModel>,
    private val handler: MovieListEventHandler,
) : ListAdapter<FeedUiModel, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<FeedUiModel>() {
            override fun areItemsTheSame(
                oldItem: FeedUiModel,
                newItem: FeedUiModel,
            ): Boolean {
                return when {
                    oldItem is MovieUiModel && newItem is MovieUiModel && oldItem.id == newItem.id -> true
                    oldItem is AdUiModel && newItem is AdUiModel && oldItem.imgResource == newItem.imgResource -> true
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: FeedUiModel,
                newItem: FeedUiModel,
            ): Boolean {
                return oldItem == newItem
            }
        },
    ) {
    override fun getItemCount(): Int = itemsList.size

    override fun getItemViewType(position: Int): Int =
        when (itemsList[position]) {
            is MovieUiModel -> VIEW_TYPE_MOVIE
            is AdUiModel -> VIEW_TYPE_AD
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_AD -> AdViewHolder(parent)
            VIEW_TYPE_MOVIE -> MovieViewHolder(parent, handler)
            else -> throw IllegalStateException()
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = itemsList[position]

        when (holder) {
            is AdViewHolder -> holder.bind(item as AdUiModel)
            is MovieViewHolder -> holder.bind(item as MovieUiModel)
        }
    }

    companion object {
        private val VIEW_TYPE_MOVIE = R.layout.movie_item
        private val VIEW_TYPE_AD = R.layout.ad_item
    }
}
