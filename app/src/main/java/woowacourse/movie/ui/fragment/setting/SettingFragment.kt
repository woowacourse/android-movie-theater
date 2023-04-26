package woowacourse.movie.ui.fragment.setting

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
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
            val sharedPreference = it.getSharedPreferences(SETTINGS, MODE_PRIVATE)
            switchValue = sharedPreference.getBoolean(NOTIFICATIONS, false)
            switch?.setOnCheckedChangeListener { _, isChecked ->
                val editor = sharedPreference.edit()
                editor.putBoolean(NOTIFICATIONS, isChecked).apply()
            }
        }
        switch?.isChecked = switchValue
    }

    override fun onDestroyView() {
        super.onDestroyView()
        switch = null
    }

    companion object {
        private const val SETTINGS = "settings"
        private const val NOTIFICATIONS = "notifications"
    }
}
