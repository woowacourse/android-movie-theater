package woowacourse.movie.presentation.model.message

import android.content.Context

sealed interface MessageType {
    fun toMessage(context: Context): String
}
