package woowacourse.movie.movielist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemScreeningAdvertiseBinding
import woowacourse.movie.databinding.ItemScreeningMovieBinding
import woowacourse.movie.movielist.uimodel.ScreenMovieUiModel

sealed class ScreeningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MovieViewHolder(
    private val binding: ItemScreeningMovieBinding,
    private val adapterClickListener: AdapterClickListener,
) : ScreeningViewHolder(binding.root) {
    fun onBind(item: ScreenMovieUiModel) {
        binding.movie = item
        binding.clickListener = adapterClickListener
    }
}

class AdvertiseViewHolder(binding: ItemScreeningAdvertiseBinding) : ScreeningViewHolder(binding.root)
