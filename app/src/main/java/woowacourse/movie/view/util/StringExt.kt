package woowacourse.movie.view.util

import android.content.Context

fun String.toDrawableResourceId(context: Context): Int {
    return context.resources.getIdentifier(this, "drawable", context.packageName)
}
