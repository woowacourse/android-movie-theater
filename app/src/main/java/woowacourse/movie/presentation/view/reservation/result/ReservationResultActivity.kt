package woowacourse.movie.presentation.view.reservation.result

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel

class ReservationResultActivity : BaseActivity() {
    override fun getLayoutResId(): Int = R.layout.activity_reservation_result

    private lateinit var binding: ActivityReservationResultBinding

    override fun onCreateSetup(savedInstanceState: Bundle?) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_result)
        setUpFromIntent()
    }

    private fun setUpFromIntent() {
        val ticket = intent.getParcelableExtra<MovieTicketUiModel>(INTENT_TICKET)
        binding.data = ticket
    }

    companion object {
        const val INTENT_TICKET = "ticket"
    }
}
