package woowacourse.movie.presentation.binding.adapter

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import woowacourse.movie.presentation.views.ticketing.listener.OnSpinnerItemSelectedListener

@BindingAdapter("app:onItemClick")
fun Spinner.setOnItemClick(listener: (Int) -> Unit) {
    onItemSelectedListener = OnSpinnerItemSelectedListener { position ->
        listener(position)
    }
}
