package woowacourse.movie.ui.fragment.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.PreferenceUtil
import woowacourse.movie.R

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
            switch?.setOnCheckedChangeListener { _, isChecked ->
                sharedPreference.setBoolean(NOTIFICATIONS, isChecked)
                Log.d("mendel", "수신여부 변경 후 저장값: ${sharedPreference.getBoolean(NOTIFICATIONS, false)}")
            }
        }
        switch?.isChecked = switchValue
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
