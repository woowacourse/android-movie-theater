package woowacourse.movie.view.core.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun Context.showToastFromResource(
    @StringRes resourceId: Int,
    duration: Int = Toast.LENGTH_SHORT,
) {
    Toast.makeText(this, resourceId, duration).show()
}

fun Context.showPermissionSnackBar(view: View) {
    Snackbar.make(view, "권한이 거부 되었습니다. 설정(앱 정보)에서 권한을 확인해 주세요.", Snackbar.LENGTH_SHORT)
        .setAction("확인") {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val packageName = this.packageName
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri

            this.startActivity(intent)
        }.show()
}
