package woowacourse.movie.view.base

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>(val layoutId: Int) : Fragment() {
    protected lateinit var binding: T
    private var _binding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding =
            DataBindingUtil.inflate(
                inflater,
                layoutId,
                container,
                false,
            )
        binding = _binding!!
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun hasNotificationPermission(): Boolean =
        ContextCompat.checkSelfPermission(requireContext(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED

    companion object {
        protected const val POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS"
    }
}
