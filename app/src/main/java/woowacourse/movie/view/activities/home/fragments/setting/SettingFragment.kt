package woowacourse.movie.view.activities.home.fragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.view.utils.getPushAlarmReceptionIsWanted
import woowacourse.movie.view.utils.isPostingNotificationPermissionGranted
import woowacourse.movie.view.utils.setPushAlarmReceptionIsWanted
import woowacourse.movie.view.utils.showDialogToGetPostNotificationPermission

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val switch = view.findViewById<SwitchCompat>(R.id.switch_push_alarm)
        switch.isChecked = isReceivingPushNotificationWanted()
        switch.setOnCheckedChangeListener { switchView, isChecked ->
            setPushAlarmReceptionIsWanted(requireContext(), isChecked)
            if (isChecked && isPostingNotificationPermissionGranted(requireContext()).not()) {
                showDialogToGetPostNotificationPermission(requireContext())
            }
            switchView.isChecked = isReceivingPushNotificationWanted()
        }
    }

    private fun isReceivingPushNotificationWanted(): Boolean {
        return getPushAlarmReceptionIsWanted(requireContext())
                && isPostingNotificationPermissionGranted(requireContext())
    }
}
