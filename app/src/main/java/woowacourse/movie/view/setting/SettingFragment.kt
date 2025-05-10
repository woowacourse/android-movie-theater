package woowacourse.movie.view.setting

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var alarmBtn: SwitchCompat

    private val sharedPref by lazy {
        requireActivity().getSharedPreferences("setting", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val view = binding.root

        val isAlarmOn = sharedPref.getBoolean("isAlarmOn", false)
        setAlarmBtn(isAlarmOn)

        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAlarmBtn(isAlarmOn: Boolean) {
        alarmBtn = binding.settingAlarmSwitchBtn
        alarmBtn.isChecked = isAlarmOn

        alarmBtn.setOnCheckedChangeListener { _, isChecked ->

            sharedPref.edit().putBoolean("isAlarmOn", isChecked).apply()

            if (isChecked) {
                setAlarm()
            } else {
                cancelAlarm()
            }
        }
    }

    private fun setAlarm() {
        Log.d("alarm", "on")
    }

    private fun cancelAlarm() {
        Log.d("alarm", "off")
    }
}
