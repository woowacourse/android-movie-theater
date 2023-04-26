package woowacourse.movie.presentation.activities.main.fragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.woowacourse.data.local.Preferences
import woowacourse.movie.R

class SettingFragment : Fragment() {
    private val preferences by lazy { Preferences(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPushSwitch(view)
    }

    private fun initPushSwitch(view: View) {
        val switch = view.findViewById<SwitchMaterial>(R.id.notification_push_switch)
        with(switch) {
            isChecked = preferences.getBoolean(PUSH_ALLOW_KEY, true)
            setOnCheckedChangeListener { _, isChecked ->
                preferences.setBoolean(PUSH_ALLOW_KEY, isChecked)
            }
        }
    }

    companion object {
        private val settingFragment = SettingFragment()
        internal const val PUSH_ALLOW_KEY = "push_allow_key"

        fun newInstance(): SettingFragment = settingFragment.apply {
            arguments = Bundle().apply {
            }
        }
    }
}
