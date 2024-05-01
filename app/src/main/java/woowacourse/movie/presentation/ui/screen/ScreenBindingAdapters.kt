package woowacourse.movie.presentation.ui.screen

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.presentation.ui.screen.adapter.ScreenRecyclerViewAdapter

@BindingAdapter("bindUpdateScreensRecyclerView")
fun RecyclerView.updateScreensRecyclerView(screens: List<ScreenView>) {
    if (this.adapter is ScreenRecyclerViewAdapter) {
        (this.adapter as ScreenRecyclerViewAdapter).updateScreens(screens)
    }
}
