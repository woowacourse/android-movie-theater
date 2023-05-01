package woowacourse.movie.view.error

import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import woowacourse.movie.R

object BroadcastReceiverError {

    fun BroadcastReceiver.returnWithError(
        missingExtras: ViewError.MissingExtras,
        context: Context
    ) {
        Log.d(
            context.getString(R.string.log_tag),
            context.getString(
                R.string.broadcast_error_log_message,
                javaClass.name,
                missingExtras.message
            )
        )
    }
}
