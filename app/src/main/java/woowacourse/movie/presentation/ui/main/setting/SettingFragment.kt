package woowacourse.movie.presentation.ui.main.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null

    private val binding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        activity?.let {
            val pref = it.getPreferences(Context.MODE_PRIVATE)
            val value = pref.getBoolean(PREF_KEY, false)
            binding.btnSetting.text = value.toString()
            initButton()
        }
    }

    private fun initButton() {
        binding.btnSetting.setOnClickListener {
            moveToSetting()
        }
    }

    private fun moveToSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val packageName = context?.packageName ?: return
        intent.data = Uri.parse("package:$packageName")
        context?.startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val PREF_KEY = "notification_permission"
    }
}
