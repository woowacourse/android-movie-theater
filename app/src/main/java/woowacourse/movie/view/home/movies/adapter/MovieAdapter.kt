package woowacourse.movie.view.home.movies.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.home.model.UiModel
import woowacourse.movie.view.home.model.UiModel.AdvertiseUiModel
import woowacourse.movie.view.home.model.UiModel.MovieUiModel
import woowacourse.movie.view.home.movies.MovieListEventHandler
import woowacourse.movie.view.home.movies.viewholder.AdvertiseViewHolder
import woowacourse.movie.view.home.movies.viewholder.MovieViewHolder

class MovieAdapter(
    private val itemsList: List<UiModel>,
    private val handler: MovieListEventHandler,
) : ListAdapter<UiModel, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(
                oldItem: UiModel,
                newItem: UiModel,
            ): Boolean {
                return when {
                    oldItem is MovieUiModel && newItem is MovieUiModel && oldItem.id == newItem.id -> true
                    oldItem is AdvertiseUiModel && newItem is AdvertiseUiModel && oldItem.imgResource == newItem.imgResource -> true
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: UiModel,
                newItem: UiModel,
            ): Boolean {
                return oldItem == newItem
            }
        },
    ) {
    override fun getItemCount(): Int = itemsList.size

    override fun getItemViewType(position: Int): Int =
        when (itemsList[position]) {
            is MovieUiModel -> VIEW_TYPE_MOVIE
            is AdvertiseUiModel -> VIEW_TYPE_ADVERTISEMENT
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_ADVERTISEMENT -> AdvertiseViewHolder(parent)
            VIEW_TYPE_MOVIE -> MovieViewHolder(parent, handler)
            else -> throw IllegalStateException()
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = itemsList[position]

        when (holder) {
            is AdvertiseViewHolder -> holder.bind(item as UiModel.AdvertiseUiModel)
            is MovieViewHolder -> holder.bind(item as UiModel.MovieUiModel)
        }
    }

    companion object {
        private val VIEW_TYPE_MOVIE = R.layout.movie_item
        private val VIEW_TYPE_ADVERTISEMENT = R.layout.advertisement_item
    }
}
