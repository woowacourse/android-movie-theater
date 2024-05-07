package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderScreenBinding
import woowacourse.movie.domain.model.ScreenAd

class ScreenViewHolder(
    private val onReserveClick: (id: Int) -> Unit,
    private val binding: HolderScreenBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(screen: ScreenAd.ScreenPreviewUi) {
        binding.screenAd = screen

        binding.btnReserveNow.setOnClickListener {
            onReserveClick(screen.id)
        }
    }
}
