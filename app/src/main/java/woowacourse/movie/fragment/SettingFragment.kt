package woowacourse.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.R
import woowacourse.movie.SharedPreferenceUtil

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        val switch = view.findViewById<SwitchMaterial>(R.id.sw_setting_can_push)
        val sharedPreferenceUtil = SharedPreferenceUtil(view.context)
        switch.isChecked = sharedPreferenceUtil.getSettingValue("switch", false)
        switch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferenceUtil.setSettingValue("switch", isChecked)
        }
        return view
    }
}
