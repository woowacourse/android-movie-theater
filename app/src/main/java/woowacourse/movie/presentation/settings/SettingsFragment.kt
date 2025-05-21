package woowacourse.movie.presentation.settings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingsBinding

class SettingsFragment :
    Fragment(),
    SettingsContract.View {
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!
    private lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = requireActivity().application as MovieApplication
        presenter = SettingsPresenter(this, app.settingRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initNotificationSwitch()
        presenter.loadSettings()
    }

    override fun updateNotificationSetting(isChecked: Boolean) {
        binding.isNotificationChecked = isChecked
    }

    private fun initNotificationSwitch() {
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!isNotificationPermissionGranted()) {
                    showPermissionExplanationDialog()
                    binding.isNotificationChecked = false
                    presenter.saveNotificationSetting(false)
                } else {
                    presenter.saveNotificationSetting(true)
                }
            } else {
                presenter.saveNotificationSetting(false)
            }
        }
    }

    private fun isNotificationPermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED

    private fun showPermissionExplanationDialog() {
        AlertDialog
            .Builder(requireContext())
            .setTitle(R.string.notification_permission_text)
            .setMessage(R.string.notification_permission_message)
            .setPositiveButton(R.string.confirm_text, null)
            .setCancelable(false)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
