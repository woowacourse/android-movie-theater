package woowacourse.movie.view.bindingadapter

import android.widget.Button
import androidx.databinding.BindingAdapter

@BindingAdapter("enabledAlpha")
fun setButtonEnabledAlpha(
    button: Button,
    isEnabled: Boolean,
) {
    button.isClickable = isEnabled
    button.alpha = if (isEnabled) 1f else 0.1f
}
