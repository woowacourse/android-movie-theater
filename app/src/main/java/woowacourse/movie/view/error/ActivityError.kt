package woowacourse.movie.view.error

import android.app.Activity
import android.util.Log
import android.widget.Toast
import woowacourse.movie.R

object ActivityError {

    fun Activity.finishWithError(message: String) {
        Log.d(
            getString(R.string.log_tag),
            getString(R.string.activity_error_log_message, localClassName, message),
        )
        Toast.makeText(this, getString(R.string.activity_error_toast_message), Toast.LENGTH_SHORT)
            .show()
        finish()
    }
}
