package woowacourse.movie.ui.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.util.PreferenceUtil
import woowacourse.movie.R
import woowacourse.movie.ui.Toaster
import woowacourse.movie.main.MainActivity.Companion.PERMISSIONS
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
        var switchValue = false
        context?.let {
            val sharedPreference = PreferenceUtil(it)
            switchValue = sharedPreference.getBoolean(NOTIFICATIONS, false)
            switch?.isChecked = switchValue
            switch?.setOnCheckedChangeListener { switchCompat, _ ->
                val permission = this.activity?.hasPermissions(PERMISSIONS) ?: return@setOnCheckedChangeListener
                if (!permission) {
                    switchCompat.isChecked = false
                    Toaster.showToast(it, "알림 권한을 허용해주세요.")
                }
                sharedPreference.setBoolean(NOTIFICATIONS, switchCompat.isChecked)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        switch = null
    }

    companion object {
        const val SETTINGS = "settings"
        const val NOTIFICATIONS = "notifications"
    }
}
