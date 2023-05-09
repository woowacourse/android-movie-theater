package woowacourse.movie.view.moviemain.movielist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.model.MovieUiModel

class MovieListAdapter(
    private val adInterval: Int,
    private val movies: List<MovieUiModel>,
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun interface OnItemClick {
        fun onClick(item: MovieUiModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder.of(parent, ListViewType.values()[viewType])
    }

    override fun getItemCount(): Int = movies.size + movies.size / adInterval

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = movies[position - ((position + 1) / (adInterval + 1))]
        when (holder) {
            is ItemViewHolder.MovieItemViewHolder -> {
                holder.set(item) {
                    onItemClick.onClick(item)
                }
            }
            is ItemViewHolder.AdItemViewHolder -> {
                holder.set(R.drawable.woowacourse_banner)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ListViewType.getViewType(adInterval, position).ordinal
    }
}
