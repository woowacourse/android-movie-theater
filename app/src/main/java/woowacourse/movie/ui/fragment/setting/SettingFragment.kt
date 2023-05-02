package woowacourse.movie.ui.fragment.setting

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var switch: SwitchCompat

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switch = view.findViewById(R.id.notification_switch)

        switch.setOnCheckedChangeListener { switchCompat, _ -> }
    }

    companion object {
        const val NOTIFICATIONS = "notifications"
    }
}
