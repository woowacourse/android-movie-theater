package woowacourse.movie.feature.common.customView

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import woowacourse.movie.R

class SeatView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var isChecked: Boolean = false
        private set

    init {
        setBackgroundColor(ContextCompat.getColor(context, R.color.seat_no_selected))
    }

    fun changeSelected() {
        when (isChecked) {
            true -> setBackgroundColor(ContextCompat.getColor(context, R.color.seat_no_selected))
            false -> setBackgroundColor(ContextCompat.getColor(context, R.color.seat_selected))
        }
        isChecked = isChecked.not()
    }
}
