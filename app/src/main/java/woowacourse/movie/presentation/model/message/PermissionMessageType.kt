package woowacourse.movie.presentation.model.message

import android.content.Context
import woowacourse.movie.R

sealed class PermissionMessageType : MessageType {
    data class RequestPermissionMessage(val permission: String) : PermissionMessageType() {
        override fun toMessage(context: Context): String =
            context.getString(
                R.string.request_permission_message,
                this.permission,
            )
    }
}
