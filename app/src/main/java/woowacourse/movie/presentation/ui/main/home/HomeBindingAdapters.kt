package woowacourse.movie.presentation.ui.main.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R

@BindingAdapter("bindSetTheaterCount")
fun TextView.setTheaterCount(count: Int) {
    this.text = this.context.getString(R.string.theater_count, count)
}
