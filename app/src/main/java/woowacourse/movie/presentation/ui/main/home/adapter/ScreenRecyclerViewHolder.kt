package woowacourse.movie.presentation.ui.main.home.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderAdsBinding
import woowacourse.movie.databinding.HolderScreenBinding
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.ScreenView.Movie
import woowacourse.movie.presentation.ui.main.home.ScreenActionHandler

sealed class ScreenRecyclerViewHolder(binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    class ScreenViewHolder(
        private val binding: HolderScreenBinding,
        private val screenActionHandler: ScreenActionHandler,
    ) : ScreenRecyclerViewHolder(binding) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.handler = screenActionHandler
        }
    }

    class AdsViewHolder(
        private val binding: HolderAdsBinding,
    ) : ScreenRecyclerViewHolder(binding) {
        fun bind(ads: ScreenView.Ads) {
            binding.ads = ads
        }
    }
}
