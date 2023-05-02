package woowacourse.movie.view.error

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.movie.R

object FragmentError {

    fun Fragment.finishWithError(missingExtras: ViewError.MissingExtras) {
        Log.d(
            getString(R.string.log_tag),
            getString(R.string.fragment_error_log_message, this, missingExtras.message)
        )
        Toast.makeText(
            activity,
            getString(R.string.fragment_error_toast_message),
            Toast.LENGTH_SHORT
        ).show()
    }
}
