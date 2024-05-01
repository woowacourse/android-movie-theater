package woowacourse.movie.presentation.ui.screen

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.presentation.ui.screen.adapter.ScreenRecyclerViewAdapter

@BindingAdapter("bindUpdateScreensRecyclerView")
fun RecyclerView.updateScreensRecyclerView(screens: List<ScreenView>) {
    if (this.adapter is ScreenRecyclerViewAdapter) {
        (this.adapter as ScreenRecyclerViewAdapter).updateScreens(screens)
    }
}

@BindingAdapter("bindSetTheaterCount")
fun TextView.setTheaterCount(count: Int) {
    this.text = this.context.getString(R.string.theater_count, count)
}
