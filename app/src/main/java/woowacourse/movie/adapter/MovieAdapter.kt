package woowacourse.movie.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.model.itemmodel.AdvertisementItemModel
import woowacourse.movie.data.model.itemmodel.MovieItemModel
import woowacourse.movie.data.model.uimodel.AdvertisementUIModel
import woowacourse.movie.data.model.uimodel.MovieAdapterViewType
import woowacourse.movie.data.model.uimodel.MovieUIModel
import woowacourse.movie.viewholder.AdvertisementItemViewHolder
import woowacourse.movie.viewholder.MovieItemViewHolder

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var _movies: List<MovieItemModel> = listOf()
    val movies
        get() = _movies.toList()
    private var _advertisement: AdvertisementItemModel? = null
    val advertisement
        get() = _advertisement!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (MovieAdapterViewType.find(viewType)) {
            MovieAdapterViewType.MOVIE -> MovieItemViewHolder(parent)
            MovieAdapterViewType.ADVERTISEMENT -> AdvertisementItemViewHolder(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position % CYCLE) {
            ADVERTISEMENT_TURN -> MovieAdapterViewType.ADVERTISEMENT.value
            else -> MovieAdapterViewType.MOVIE.value
        }
    }

    override fun getItemCount(): Int = movies.size + (movies.size / ADVERTISEMENT_TURN)

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is AdvertisementItemViewHolder -> viewHolder.bind(advertisement)
            is MovieItemViewHolder -> viewHolder.bind(movies[position - (position / CYCLE)])
        }
    }

    fun updateMovieItems(
        movies: List<MovieUIModel>,
        onClick: (MovieUIModel) -> Unit
    ) {
        _movies = movies.map { movie ->
            movie.toItemModel { onClick(movie) }
        }
    }

    fun updateAdvertisementItems(
        advertisement: AdvertisementUIModel,
        onClick: (AdvertisementUIModel) -> Unit
    ) {
        _advertisement = advertisement.toItemModel { onClick(advertisement) }
    }

    companion object {
        private const val ADVERTISEMENT_TURN = 3
        private const val CYCLE = 4
    }
}
