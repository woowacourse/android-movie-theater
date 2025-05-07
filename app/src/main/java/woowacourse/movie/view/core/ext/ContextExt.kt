package woowacourse.movie.view.core.ext

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToastFromResource(
    @StringRes resourceId: Int,
    duration: Int = Toast.LENGTH_SHORT,
) {
    Toast.makeText(this, resourceId, duration).show()
}
