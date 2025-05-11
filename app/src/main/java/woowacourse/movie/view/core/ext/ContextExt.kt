package woowacourse.movie.view.core.ext

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToastFromResource(
    @StringRes resourceId: Int,
    duration: Int = Toast.LENGTH_SHORT,
) {
    Toast.makeText(this, resourceId, duration).show()
}

fun Context.checkNotificationPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}
