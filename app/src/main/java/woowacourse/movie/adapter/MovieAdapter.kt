package woowacourse.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.model.itemmodel.AdvertisementItemModel
import woowacourse.movie.data.model.itemmodel.MovieItemModel
import woowacourse.movie.data.model.uimodel.AdvertisementUiModel
import woowacourse.movie.data.model.uimodel.MovieAdapterViewType
import woowacourse.movie.data.model.uimodel.MovieUiModel
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemMovieBinding
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
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (MovieAdapterViewType.find(viewType)) {
            MovieAdapterViewType.MOVIE -> MovieItemViewHolder(
                ItemMovieBinding.inflate(layoutInflater, parent, false)
            )
            MovieAdapterViewType.ADVERTISEMENT -> AdvertisementItemViewHolder(
                ItemAdvertisementBinding.inflate(layoutInflater, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position % CYCLE) {
            ADVERTISEMENT_TURN -> MovieAdapterViewType.ADVERTISEMENT.value
            else -> MovieAdapterViewType.MOVIE.value
        }
    }

    override fun getItemCount(): Int = 10_000

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is AdvertisementItemViewHolder -> viewHolder.bind(advertisement)
            is MovieItemViewHolder -> viewHolder.bind(movies[position])
        }
    }

    fun updateMovieItems(
        movies: List<MovieUiModel>,
        onClick: (MovieUiModel) -> Unit
    ) {
        _movies = movies.map { movie ->
            movie.toItemModel { onClick(movie) }
        }
    }

    fun updateAdvertisementItems(
        advertisement: AdvertisementUiModel,
        onClick: (AdvertisementUiModel) -> Unit
    ) {
        _advertisement = advertisement.toItemModel(onClick)
    }

    companion object {
        private const val ADVERTISEMENT_TURN = 3
        private const val CYCLE = 4
    }
}
