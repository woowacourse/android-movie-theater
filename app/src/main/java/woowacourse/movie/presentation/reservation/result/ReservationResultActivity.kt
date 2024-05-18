package woowacourse.movie.presentation.reservation.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.IntentCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.navigation.NavigationActivity
import woowacourse.movie.presentation.notification.MovieNotificationAlarmManager
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel

class ReservationResultActivity : BaseActivity() {
    override fun getLayoutResId(): Int = R.layout.activity_reservation_result

    private lateinit var binding: ActivityReservationResultBinding

    override fun onCreateSetup(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        setUpFromIntent()
    }

    private fun setUpFromIntent() {
        val ticket =
            IntentCompat.getParcelableExtra(intent, INTENT_TICKET, MovieTicketUiModel::class.java)
        ticket?.let {
            binding.data = ticket
            MovieNotificationAlarmManager.init(this, ticketUiModel = ticket)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = NavigationActivity.getIntent(this)
                startActivity(intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val INTENT_TICKET = "ticket"

        fun getIntent(context: Context): Intent {
            return Intent(context, ReservationResultActivity::class.java)
        }
    }
}
