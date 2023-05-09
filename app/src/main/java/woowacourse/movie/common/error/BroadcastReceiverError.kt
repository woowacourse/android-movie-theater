package woowacourse.movie.common.error

import android.content.BroadcastReceiver
import android.util.Log

object BroadcastReceiverError {
    private const val LOG_TAG = "DYDY"
    private const val ERROR_LOG_MESSAGE = "%s 브로드캐스트 리시버에서 %s Extra가 필요합니다."

    fun BroadcastReceiver.returnWithError(missingExtras: ViewError.MissingExtras) {
        Log.d(LOG_TAG, ERROR_LOG_MESSAGE.format(javaClass.name, missingExtras.message))
    }
}
