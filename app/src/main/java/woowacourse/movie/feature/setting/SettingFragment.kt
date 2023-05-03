package woowacourse.movie.feature.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.AlarmSettingRepositoryImpl
import woowacourse.movie.feature.common.Toaster
import woowacourse.movie.feature.main.MainActivity.Companion.PERMISSIONS
import woowacourse.movie.util.hasPermissions

class SettingFragment : Fragment() {

    private var switch: SwitchCompat? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switch = view.findViewById(R.id.notification_switch)
        val switchValue = AlarmSettingRepositoryImpl.enablePushNotification
        switch?.isChecked = switchValue
        switch?.setOnCheckedChangeListener { _, _ ->
            checkedChange()
        }
    }

    private fun checkedChange() {
        switch?.let {
            val permission =
                this.activity?.hasPermissions(PERMISSIONS) ?: return
            if (!permission) {
                it.isChecked = false
                Toaster.showToast(
                    requireContext(),
                    getString(R.string.alarm_premission_request_message)
                )
            }
            AlarmSettingRepositoryImpl.enablePushNotification = it.isChecked
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        switch = null
    }
}
