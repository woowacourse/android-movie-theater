package woowacourse.movie.ui.adv

import woowacourse.movie.databinding.ActivityAdvDetailBinding
import woowacourse.movie.model.AdvState

class AdvDetailView(
    binding: ActivityAdvDetailBinding,
    advState: AdvState
) {
    init {
        binding.advDetailImg.setImageResource(advState.imgId)
        binding.advDescription.text = advState.advDescription
    }
}
