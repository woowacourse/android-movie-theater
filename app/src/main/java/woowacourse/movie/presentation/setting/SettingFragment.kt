package woowacourse.movie.presentation.setting

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import woowacourse.movie.MovieReservationApp
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presentation.NotificationPermissionLauncher
import woowacourse.movie.presentation.base.BindingFragment

class SettingFragment : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val notificationPreference by lazy { (requireActivity().application as MovieReservationApp).notificationPreference }
    private var launcher: NotificationPermissionLauncher? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        launcher = context as NotificationPermissionLauncher
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchAlarm.isChecked = notificationPreference.canNotification

        binding.root.setOnClickListener {
            notificationPreference.canNotification = !notificationPreference.canNotification
            binding.switchAlarm.isChecked = notificationPreference.canNotification
        }
    }


    override fun onDetach() {
        launcher = null
        super.onDetach()
    }

    fun updateSwitch(isGranted: Boolean) {
        binding.switchAlarm.isChecked = isGranted
    }

    private fun showToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.request_notification_permission),
            Toast.LENGTH_SHORT
        ).show()
    }
}