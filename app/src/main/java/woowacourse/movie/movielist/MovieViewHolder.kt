package woowacourse.movie.movielist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemScreeningAdvertiseBinding
import woowacourse.movie.databinding.ItemScreeningMovieBinding
import woowacourse.movie.movielist.uimodel.MovieUiModel

sealed class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MovieViewHolder(
    private val binding: ItemScreeningMovieBinding,
    private val adapterClickListener: AdapterClickListener,
) : ListItemViewHolder(binding.root) {
    fun onBind(item: MovieUiModel) {
        binding.movie = item
        binding.clickListener = adapterClickListener
    }
}

class AdvertiseViewHolder(binding: ItemScreeningAdvertiseBinding) : ListItemViewHolder(binding.root)
