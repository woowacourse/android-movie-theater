package woowacourse.movie.presentation.home.movies.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import woowacourse.movie.presentation.common.base.BaseViewHolder
import woowacourse.movie.presentation.home.movies.adapter.item.MovieMainItem

class MoviesAdapter(
    private val eventListener: OnMovieEventListener,
) : ListAdapter<MovieMainItem, BaseViewHolder<MovieMainItem, ViewBinding>>(MoviesDiffUtil) {
    override fun getItemViewType(position: Int): Int = getItem(position).viewType

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<MovieMainItem, ViewBinding> =
        when (MovieMainItem.MovieViewType.entries[viewType]) {
            MovieMainItem.MovieViewType.MOVIE -> MovieViewHolder(parent, eventListener)
            MovieMainItem.MovieViewType.AD -> AdViewHolder(parent)
        } as BaseViewHolder<MovieMainItem, ViewBinding>

    override fun onBindViewHolder(
        holder: BaseViewHolder<MovieMainItem, ViewBinding>,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    interface OnMovieEventListener : MovieViewHolder.OnMovieEventListener
}
