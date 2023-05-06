package woowacourse.movie.view.activities.home.fragments.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.view.utils.getPushAlarmReceptionIsWanted
import woowacourse.movie.view.utils.postNotificationPermissionIsGranted
import woowacourse.movie.view.utils.setPushAlarmReceptionIsWanted

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
        switch.isChecked = userWantsToReceivePushAlarm()
        switch.setOnCheckedChangeListener { switchView, isChecked ->
            setPushAlarmReceptionIsWanted(requireContext(), isChecked)
            if (isChecked && postNotificationPermissionIsGranted(requireContext()).not()) {
                showDialogToGetPostNotificationPermission(requireContext())
            }
            switchView.isChecked = userWantsToReceivePushAlarm()
        }
    }

    private fun userWantsToReceivePushAlarm(): Boolean {
        return getPushAlarmReceptionIsWanted(requireContext())
                && postNotificationPermissionIsGranted(requireContext())
    }

    private fun showDialogToGetPostNotificationPermission(context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(R.string.post_notification_permission_request_dialog_title))
            setMessage(context.getString(R.string.post_notification_permission_request_dialog_message))
            setPositiveButton(context.getString(R.string.ok)) { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            setNegativeButton(context.getString(R.string.cancel)) { _, _ -> }
        }
            .create()
            .show()
    }
}
