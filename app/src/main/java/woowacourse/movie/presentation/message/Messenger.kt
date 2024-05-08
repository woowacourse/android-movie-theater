package woowacourse.movie.presentation.message

import woowacourse.movie.presentation.model.MessageType

object Messenger {
    private lateinit var message: Message

    fun init(message: Message) {
        this.message = message
    }

    fun showToast(message: String) = this.message.showToast(message)

    fun showToast(e: Throwable) = this.message.showToast(e)

    fun showToast(messageType: MessageType) = this.message.showToast(messageType)

    fun showSnackBar(message: String) = this.message.showSnackBar(message)

    fun showSnackBar(e: Throwable) = this.message.showSnackBar(e)

    fun showSnackBar(messageType: MessageType) = this.message.showSnackBar(messageType)
}
