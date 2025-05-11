package woowacourse.movie.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import woowacourse.movie.ui.settings.view.NotificationSwitchListener

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageRes")
    fun loadImage(
        view: ImageView,
        @DrawableRes imageRes: Int,
    ) {
        view.setImageResource(imageRes)
    }

    @JvmStatic
    @BindingAdapter("onCheckedChanged")
    fun setOnCheckedChangeListener(
        switch: SwitchCompat,
        listener: NotificationSwitchListener,
    ) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            listener.onClick(isChecked)
        }
    }
}
