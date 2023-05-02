package woowacourse.movie.view.error

import android.app.Activity
import android.util.Log
import android.widget.Toast
import woowacourse.movie.R

object ActivityError {

    fun Activity.finishWithError(missingExtras: ViewError.MissingExtras) {
        Log.d(
            getString(R.string.log_tag),
            getString(R.string.activity_error_log_message, localClassName, missingExtras.message)
        )
        Toast.makeText(this, getString(R.string.activity_error_toast_message), Toast.LENGTH_SHORT)
            .show()
        finish()
    }
}
