package woowacourse.movie.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.Setting

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        makeNotificationSwitchCompat(view)
        return view
    }

    private fun makeNotificationSwitchCompat(view: View) {
        view.findViewById<SwitchCompat>(R.id.setting_push_switch)
            .setOnCheckedChangeListener { buttonView, isChecked ->
                Setting.setSettingValue(buttonView.context, SETTING_NOTIFICATION, isChecked)
            }
    }

    companion object {
        const val SETTING_NOTIFICATION = "notification"
    }
}
