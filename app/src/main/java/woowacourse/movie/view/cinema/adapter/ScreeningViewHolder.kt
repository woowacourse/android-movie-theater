package woowacourse.movie.view.cinema.adapter

import android.view.View
import woowacourse.movie.databinding.ItemScreeningBinding
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ScreeningContent
import woowacourse.movie.view.reservation.Poster.posterId

class ScreeningViewHolder(
    private val binding: ItemScreeningBinding,
    private val onClickReserveButton: (Screening) -> Unit,
) : ScreeningContentViewHolder(binding.root) {
    override fun bind(item: ScreeningContent) {
        val screening = item as? Screening ?: error("")
        binding.screening = screening
        binding.posterDrawable = screening.posterId()
        binding.onClickReserveListener = View.OnClickListener { onClickReserveButton(screening) }
    }
}
