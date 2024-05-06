package woowacourse.movie.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.NotificationCompat.getColor
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.ScreenAd
import woowacourse.movie.domain.model.Seat
import java.util.Locale

@BindingAdapter("app:textFormat")
fun textUiFormat(
    textView: TextView,
    seats: List<Seat>,
) {
    textView.text =
        seats.joinToString(
            separator = ",",
            postfix = " |",
        ) { "${'A' + it.position.row}${it.position.col + 1}" }
}

@BindingAdapter("app:currency")
fun currency(
    textView: TextView,
    price: Int,
) {
    textView.text =
        String.format("%s(현장 결제)", Currency.of(Locale.getDefault().country).format(price))
}

@BindingAdapter("app:srcScreenAdvertisement")
fun screenAdvertisementImage(
    imageView: ImageView,
    screenAd: ScreenAd,
) {
    when (screenAd) {
        is ScreenAd.ScreenPreviewUi -> screenPoster(imageView, screenAd.moviePreviewUI.image)

        is ScreenAd.Advertisement -> imageView.setImageResource(screenAd.advertisement.imageSource)
    }
}

@BindingAdapter("app:dateRangeFormat")
fun screeningDateRange(
    textView: TextView,
    dateRange: DateRange,
) {
    textView.text = dateRange.toUi()
}

@BindingAdapter("app:srcScreenPoster")
fun screenPoster(
    imageView: ImageView,
    image: Image<Any>,
) {
    imageView.setImageResource(image.imageSource as Int)
}

@BindingAdapter("app:appCompatButtonEnabled")
fun buttonEnabled(
    appCompatButton: AppCompatButton,
    boolean: Boolean,
) {
    if (boolean) {
        appCompatButton.isEnabled = true
        appCompatButton.setBackgroundColor(appCompatButton.context.getColor(R.color.complete_activated))
    } else {
        appCompatButton.isEnabled = false
        appCompatButton.setBackgroundColor(appCompatButton.context.getColor(R.color.complete_deactivated))
    }
}
