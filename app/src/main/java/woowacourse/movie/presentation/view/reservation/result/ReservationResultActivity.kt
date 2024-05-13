package woowacourse.movie.presentation.view.reservation.result

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.IntentCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.presentation.view.navigation.NavigationActivity
import woowacourse.movie.presentation.view.notification.MovieNotificationAlarmManager

class ReservationResultActivity : BaseActivity() {
    override fun getLayoutResId(): Int = R.layout.activity_reservation_result

    private lateinit var binding: ActivityReservationResultBinding

    override fun onCreateSetup(savedInstanceState: Bundle?) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        setUpFromIntent()
    }

    private fun setUpFromIntent() {
        val ticket =
            IntentCompat.getParcelableExtra(intent, INTENT_TICKET, MovieTicketUiModel::class.java)
        binding.data = ticket
        MovieNotificationAlarmManager.init(this, ticketUiModel = ticket!!)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, NavigationActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val INTENT_TICKET = "ticket"
    }
}
