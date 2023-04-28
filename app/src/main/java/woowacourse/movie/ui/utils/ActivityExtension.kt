package woowacourse.movie.ui.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Activity.openAndroidSettings() {
    val uri = Uri.fromParts("package", packageName, null)
    val intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = uri
    }
    startActivity(intent)
}
