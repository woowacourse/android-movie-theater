package woowacourse.movie.view.moviemain.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieAdItemBinding
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.view.model.MovieListModel
import woowacourse.movie.view.model.MovieListModel.MovieAdModel
import woowacourse.movie.view.model.MovieListModel.MovieUiModel

class MovieListAdapter(
    private val dataList: List<MovieListModel>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun interface OnItemClickListener {
        fun onClick(item: MovieListModel)
    }

    private val onItemViewClick: (Int) -> Unit = {
        onItemClickListener.onClick(dataList[it])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (MovieListViewType.values()[viewType]) {
            MovieListViewType.MOVIE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    MovieListViewType.MOVIE_ITEM.id, parent, false
                )
                MovieItemViewHolder(MovieItemBinding.bind(view)) {
                    onItemViewClick(it)
                }
            }
            MovieListViewType.AD_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    MovieListViewType.AD_ITEM.id, parent, false
                )
                MovieAdViewHolder(MovieAdItemBinding.bind(view)) {
                    onItemViewClick(it)
                }
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        when (holder) {
            is MovieItemViewHolder -> {
                holder.bind(item as MovieUiModel)
            }
            is MovieAdViewHolder -> {
                holder.bind(item as MovieAdModel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when (dataList[position]) {
        is MovieUiModel -> MovieListViewType.MOVIE_ITEM.ordinal
        is MovieAdModel -> MovieListViewType.AD_ITEM.ordinal
    }
}
