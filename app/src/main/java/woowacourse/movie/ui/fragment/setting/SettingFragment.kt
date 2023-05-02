package woowacourse.movie.ui.fragment.setting

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.main.MainActivity.Companion.PERMISSIONS
import woowacourse.movie.ui.Toaster
import woowacourse.movie.util.PreferenceUtil
import woowacourse.movie.util.hasPermissions

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var switch: SwitchCompat

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreference = PreferenceUtil(requireContext())
        switch = view.findViewById(R.id.notification_switch)

        switch.isChecked = sharedPreference.getBoolean(NOTIFICATIONS, false)
        switch.setOnCheckedChangeListener { switchCompat, _ ->
            switchCheckedEvent(switchCompat, sharedPreference)
        }
    }

    private fun switchCheckedEvent(switchCompat: CompoundButton, sharedPreference: PreferenceUtil) {
        val permission = requireContext().hasPermissions(PERMISSIONS)
        if (!permission) {
            switchCompat.isChecked = false
            Toaster.showToast(requireContext(), "알림 권한을 허용해주세요.")
        }
        sharedPreference.setBoolean(NOTIFICATIONS, switchCompat.isChecked)
    }

    companion object {
        const val NOTIFICATIONS = "notifications"
    }
}
