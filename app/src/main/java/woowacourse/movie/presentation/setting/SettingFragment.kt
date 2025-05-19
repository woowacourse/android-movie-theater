package woowacourse.movie.presentation.setting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.notification.NotificationPreferenceImpl
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment :
    Fragment(),
    SettingContract.View {
    private val presenter: SettingContract.Presenter by lazy {
        SettingPresenter(
            this,
            NotificationPreferenceImpl.INSTANCE,
        )
    }
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchNotification.isEnabled = hasPermission()
        if (hasPermission()) {
            presenter.loadNotificationSetting()
            binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
                presenter.changeNotificationSetting(isChecked)
            }
        } else {
            showNotificationToast()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showNotificationSetting(isEnabled: Boolean) {
        binding.switchNotification.isChecked = isEnabled
    }

    private fun showNotificationToast() {
        Toast.makeText(requireContext(), getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
    }

    private fun hasPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}
