package woowacourse.movie.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

object PendingIntentGenerator {

    private var requestCode = 0

    private val flag: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

    fun generateActivityIntent(intent: Intent, context: Context): PendingIntent =
        PendingIntent.getActivity(
            context,
            requestCode++,
            intent,
            flag
        )

    fun generateBroadcastingIntent(intent: Intent, context: Context): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            requestCode++,
            intent,
            flag
        )
}
