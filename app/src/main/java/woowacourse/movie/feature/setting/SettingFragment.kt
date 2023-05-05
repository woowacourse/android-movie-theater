package woowacourse.movie.feature.setting

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.setting.MovieReminderSetting
import woowacourse.movie.feature.Toaster
import woowacourse.movie.util.hasPermission
import woowacourse.movie.util.requestPermission

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var notificationSwitch: SwitchCompat
    private val movieReminderSetting by lazy {
        MovieReminderSetting.getInstance().init(requireContext())
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNotificationSwitch(view)
        notificationSwitch.setOnCheckedChangeListener { switchCompat, _ ->
            notificationSwitchClickEvent(switchCompat)
        }
    }

    override fun onResume() {
        super.onResume()

        setNotificationSwitch()
    }

    override fun onDetach() {
        super.onDetach()
        movieReminderSetting.releaseInstance()
    }

    private fun initNotificationSwitch(view: View) {
        notificationSwitch = view.findViewById(R.id.notification_switch)
    }

    private fun setNotificationSwitch() {
        notificationSwitch.isChecked = movieReminderSetting.enabled
        if (!hasPermission(requireActivity(), Manifest.permission.POST_NOTIFICATIONS)) {
            movieReminderSetting.enabled = false
            notificationSwitch.isChecked = false
        }
    }

    private fun notificationSwitchClickEvent(switchCompat: CompoundButton) {
        val hasPermission: Boolean = hasPermission(requireActivity(), Manifest.permission.POST_NOTIFICATIONS)
        if (!movieReminderSetting.enabled && !hasPermission) {
            switchCompat.isChecked = false
            Toaster.showToast(requireContext(), "설정에서 알림 권한을 허용해주세요.")
            requestNotificationPermission()
        }
        movieReminderSetting.enabled = switchCompat.isChecked
    }

    private fun requestNotificationPermission() {
        requestPermission(
            requireActivity(),
            Manifest.permission.POST_NOTIFICATIONS,
            requestPermissionLauncher::launch
        )
    }
}
