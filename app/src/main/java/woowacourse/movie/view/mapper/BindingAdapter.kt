package woowacourse.movie.view.mapper

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.model.RunningTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@BindingAdapter("android:startDate", "android:endDate", requireAll = true)
fun setDateFormatter(
    view: TextView,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    val formatter = DateTimeFormatter.ofPattern(view.context.getString(R.string.movie_screening_period_format))
    view.text =
        view.context.getString(
            R.string.movie_date,
            startDate.format(formatter),
            endDate.format(formatter),
        )
}

@BindingAdapter("android:runningTime")
fun setRunningTime(
    view: TextView,
    runningTime: RunningTime,
) {
    view.text =
        view.context.getString(
            R.string.running_time,
            runningTime.minute.toString(),
        )
}

@BindingAdapter("android:cinemaName")
fun setCinemaName(
    view: TextView,
    cinemaName: String,
) {
    view.text =
        view.context.getString(
            R.string.cinema,
            cinemaName,
        )
}

@BindingAdapter("android:screeningTimes")
fun setScreeningItems(
    view: TextView,
    size: Int,
) {
    view.text =
        view.context.getString(
            R.string.screenig_times,
            size,
        )
}

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
