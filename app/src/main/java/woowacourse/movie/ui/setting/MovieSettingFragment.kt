package woowacourse.movie.ui.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieSettingBinding
import woowacourse.movie.ui.HandleError

class MovieSettingFragment : Fragment(), MovieSettingContract.View {
    private var _binding: FragmentMovieSettingBinding? = null
    private val binding: FragmentMovieSettingBinding
        get() = _binding!!
    private val moviePreferencesUtil by lazy { MoviePreferencesUtil(requireContext()) }
    private val presenter: MovieSettingPresenter by lazy {
        MovieSettingPresenter(
            this,
            moviePreferencesUtil,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_setting, container, false)
        presenter.loadInitialSetting()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setInitialSetting(isEnabled: Boolean) {
        binding.layoutNotificationSetting.presenter = presenter
        binding.layoutNotificationSetting.tvSettingTitle.text = "푸시 알림 수신"
        binding.layoutNotificationSetting.tvSettingDescription.text = "해제하면 푸시 알림을 수신할 수 없습니다."
        binding.layoutNotificationSetting.isNotificationActive = isEnabled
        binding.executePendingBindings()
    }

    override fun updateSwitchStatus(isEnabled: Boolean) {
        binding.layoutNotificationSetting.isNotificationActive = isEnabled
        binding.executePendingBindings()
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(requireContext(), R.string.toast_invalid_key, Toast.LENGTH_SHORT).show()
    }
}

interface MovieSettingContract {
    interface View : HandleError {
        fun setInitialSetting(isEnabled: Boolean)

        fun updateSwitchStatus(isEnabled: Boolean)
    }

    interface Presenter {
        fun loadInitialSetting()

        fun updateNotificationSelection()
    }
}

class MovieSettingPresenter(
    private val view: MovieSettingContract.View,
    private val moviePreferences: MoviePreferencesUtil,
) : MovieSettingContract.Presenter {
    override fun loadInitialSetting() {
        val isChecked = moviePreferences.getBoolean("rcv_notification")
        view.setInitialSetting(isChecked)
    }

    override fun updateNotificationSelection() {
        val targetStatus = !moviePreferences.getBoolean("rcv_notification")
        val result = moviePreferences.setBoolean("rcv_notification", targetStatus)
        view.updateSwitchStatus(result)
    }
}

class MoviePreferencesUtil(private val context: Context) {
    fun getBoolean(key: String): Boolean {
        return getSharedPreferences(context).getBoolean(key, false)
    }

    fun setBoolean(
        key: String,
        newValue: Boolean,
    ): Boolean {
        getSharedPreferences(context).edit().putBoolean(key, newValue).apply()
        return newValue
    }

    companion object {
        fun getSharedPreferences(context: Context): SharedPreferences {
            return context.applicationContext.getSharedPreferences(
                "prefs_name",
                Context.MODE_PRIVATE,
            )
        }
    }
}
