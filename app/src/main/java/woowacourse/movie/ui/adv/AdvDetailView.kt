package woowacourse.movie.ui.adv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.model.AdvState

class AdvDetailView(
    view: View,
    advState: AdvState
) {
    private val advImage: ImageView = view.findViewById(R.id.adv_detail_img)
    private val advDescription: TextView = view.findViewById(R.id.adv_description)

    init {
        advImage.setImageResource(advState.imgId)
        advDescription.text = advState.advDescription
    }
}
