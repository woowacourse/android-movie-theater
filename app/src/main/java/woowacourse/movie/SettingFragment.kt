package woowacourse.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment

class SettingFragment : Fragment() {
    private var isNotification: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isNotification = SharedPreferences.getData(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val switch: SwitchCompat = view.findViewById(R.id.notification_switch)

        switch.setOnClickListener{
            SharedPreferences.saveData(requireContext(), switch.isChecked)
            isNotification = SharedPreferences.getData(requireContext())
        }
        return view
    }
}
