package woowacourse.movie.ui.setting

import android.Manifest
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieSettingBinding
import woowacourse.movie.ui.setting.notification.MovieAlarmManager

class MovieSettingFragment : Fragment() {
    private lateinit var binding: FragmentMovieSettingBinding
    private val sharedPreference: SharedPreferences by lazy { initializeSharedPreference() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_movie_setting,
                container,
                false,
            )

        binding.swAlarmStatus.isChecked = getAlarmChecked()
        binding.swAlarmStatus.setOnCheckedChangeListener { _, isChecked ->
            setAlarmChecked(isChecked)
            if (!isChecked) {
                MovieAlarmManager.cancelAlarm(requireContext())
            } else {
                requestNotificationPermission(requireContext())
            }
        }

        return binding.root
    }

    private fun initializeSharedPreference(): SharedPreferences =
        activity?.getSharedPreferences("settings", MODE_PRIVATE)
            ?: throw IllegalStateException()

    private fun setAlarmChecked(isChecked: Boolean) {
        with(sharedPreference.edit()) {
            putBoolean("settings", isChecked)
            apply()
        }
    }

    private fun getAlarmChecked(): Boolean {
        return sharedPreference.getBoolean("settings", false)
    }

    private fun requestNotificationPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Toast.makeText(context, "알람 권한을 허용해 주세요", Toast.LENGTH_SHORT)
                    .show()
                binding.swAlarmStatus.isChecked = false
                setAlarmChecked(false)
            }
        }
    }
}
