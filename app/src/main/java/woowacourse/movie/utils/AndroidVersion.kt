package woowacourse.movie.utils

import android.os.Build

fun versionTiramisuOrHigher(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}
