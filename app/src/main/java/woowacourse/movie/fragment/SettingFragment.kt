package woowacourse.movie.fragment

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.activity.MainActivity

class SettingFragment : Fragment() {

    private lateinit var parentContext: Context
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var pushAlarmSwitch: SwitchCompat
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference =
            parentContext.getSharedPreferences(SETTING, AppCompatActivity.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        pushAlarmSwitch = view.findViewById(R.id.setting_push_alarm_switch)
        updatePushAlarmSwitch(pushAlarmSwitch)
        switchClick(pushAlarmSwitch)
        return view
    }

    private fun updatePushAlarmSwitch(pushAlarmSwitch: SwitchCompat) {
        val isPossiblePushAlarm = sharedPreference.getBoolean(PUSH_ALARM_KEY, false)
        pushAlarmSwitch.isChecked = isPossiblePushAlarm
    }

    private fun switchClick(pushAlarmSwitch: SwitchCompat) {
        pushAlarmSwitch.setOnClickListener {
            if (pushAlarmSwitch.isChecked) {
                sharedPreference.edit().putBoolean(PUSH_ALARM_KEY, true).apply()
            } else {
                sharedPreference.edit().putBoolean(PUSH_ALARM_KEY, false).apply()
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    companion object {
        private const val PUSH_ALARM_KEY = "pushAlarm"
        private const val SETTING = "settings"
    }
}
