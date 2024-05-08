package woowacourse.movie.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import woowacourse.movie.presentation.message.Messenger
import woowacourse.movie.presentation.model.MessageType

abstract class BaseMvpBindingFragment<T : ViewDataBinding> : Fragment(), BaseView {
    abstract val layoutResourceId: Int
    private var _binding: T? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initStartView()
    }

    abstract fun initStartView()

    override fun showToastMessage(messageType: MessageType) {
        requireActivity().runOnUiThread {
            Messenger.showToast(messageType)
        }
    }

    override fun showToastMessage(e: Throwable) {
        requireActivity().runOnUiThread {
            Messenger.showToast(e)
        }
    }

    override fun showSnackBar(e: Throwable) {
        requireActivity().runOnUiThread {
            Messenger.showSnackBar(binding.root, e)
        }
    }

    override fun showSnackBar(messageType: MessageType) {
        requireActivity().runOnUiThread {
            Messenger.showSnackBar(binding.root, messageType)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
