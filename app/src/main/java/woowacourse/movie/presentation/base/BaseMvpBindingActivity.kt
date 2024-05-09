package woowacourse.movie.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.presentation.message.Messenger
import woowacourse.movie.presentation.model.MessageType

abstract class BaseMvpBindingActivity<T : ViewDataBinding> : AppCompatActivity(), BaseView {
    abstract val layoutResourceId: Int
    abstract val presenter: BasePresenter
    private var _binding: T? = null
    val binding
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResourceId)
        initStartView()
    }

    abstract fun initStartView()

    override fun showToastMessage(messageType: MessageType) {
        runOnUiThread {
            Messenger.showToast(messageType)
        }
    }

    override fun showToastMessage(e: Throwable) {
        runOnUiThread {
            Messenger.showToast(e)
        }
    }

    override fun showSnackBar(e: Throwable) {
        runOnUiThread {
            Messenger.showSnackBar(binding.root, e)
        }
    }

    override fun showSnackBar(messageType: MessageType) {
        runOnUiThread {
            Messenger.showSnackBar(binding.root, messageType)
        }
    }

    override fun showSnackBar(
        messageType: MessageType,
        action: Snackbar.() -> Snackbar,
    ) {
        runOnUiThread {
            Messenger.showSnackBar(binding.root, messageType, action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
