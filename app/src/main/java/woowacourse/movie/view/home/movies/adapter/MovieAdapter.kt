package woowacourse.movie.view.home.movies.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.home.movies.HomeEventHandler
import woowacourse.movie.view.home.movies.model.UiModel
import woowacourse.movie.view.home.movies.viewholder.AdvertiseViewHolder
import woowacourse.movie.view.home.movies.viewholder.MovieViewHolder

class MovieAdapter(
    private val itemsList: List<UiModel>,
    private val handler: HomeEventHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = itemsList.size

    override fun getItemViewType(position: Int): Int =
        when (itemsList[position]) {
            is UiModel.MovieUiModel -> VIEW_TYPE_MOVIE
            is UiModel.AdvertiseUiModel -> VIEW_TYPE_ADVERTISEMENT
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
