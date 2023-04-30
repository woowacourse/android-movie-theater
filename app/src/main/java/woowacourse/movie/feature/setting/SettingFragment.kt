package woowacourse.movie.feature.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.feature.common.Toaster
import woowacourse.movie.feature.main.MainActivity.Companion.PERMISSIONS
import woowacourse.movie.global.MyApplication
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
        val switchValue = MyApplication.prefs.enablePushNotification
        switch?.isChecked = switchValue
        switch?.setOnCheckedChangeListener { switchCompat, _ ->
            val permission =
                this.activity?.hasPermissions(PERMISSIONS) ?: return@setOnCheckedChangeListener
            if (!permission) {
                switchCompat.isChecked = false
                Toaster.showToast(requireContext(), "알림 권한을 허용해주세요.")
            }
            MyApplication.prefs.enablePushNotification = switchCompat.isChecked
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        switch = null
    }
}
