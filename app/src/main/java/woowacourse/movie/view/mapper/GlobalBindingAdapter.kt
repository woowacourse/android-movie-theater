package woowacourse.movie.view.mapper

import android.widget.Button
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("android:image")
fun setImage(
    view: ImageView,
    @DrawableRes image: Int,
) {
    view.setImageResource(image)
}

@BindingAdapter("android:onClick")
fun setOnClickEventListener(
    view: Button,
    listener: () -> Unit,
) {
    view.setOnClickListener {
        listener()
    }
}
