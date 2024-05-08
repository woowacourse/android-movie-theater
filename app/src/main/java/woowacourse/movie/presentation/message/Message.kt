package woowacourse.movie.presentation.message

import woowacourse.movie.presentation.model.MessageType

interface Message {
    fun showToast(message: String)

    fun showToast(messageType: MessageType)

    fun showToast(e: Throwable)

    fun showSnackBar(message: String)

    fun showSnackBar(messageType: MessageType)

    fun showSnackBar(e: Throwable)
}
