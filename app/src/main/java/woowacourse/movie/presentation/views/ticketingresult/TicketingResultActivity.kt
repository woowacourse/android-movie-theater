package woowacourse.movie.presentation.views.ticketingresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityTicketingResultBinding
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.extensions.showBackButton
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.views.main.MainActivity
import woowacourse.movie.presentation.views.ticketingresult.contract.TicketingResultContract
import woowacourse.movie.presentation.views.ticketingresult.presenter.TicketingResultPresenter

class TicketingResultActivity : AppCompatActivity(), TicketingResultContract.View {
    override val presenter: TicketingResultContract.Presenter by lazy { makePresenter() }
    private lateinit var binding: ActivityTicketingResultBinding

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            presenter.onShowMainScreen()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ticketing_result)
        binding.reservation = presenter.getReservation()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        showBackButton()
    }

    override fun showMainScreen(reservation: Reservation) {
        startActivity(MainActivity.getIntent(this, reservation))
    }

    override fun close() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> presenter.onShowMainScreen()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makePresenter() = TicketingResultPresenter(
        view = this,
        reservation = intent.getParcelableCompat(RESERVATION_KEY)!!,
        fromMainScreen = intent.getBooleanExtra(FROM_KEY, true),
    )

    companion object {
        private const val RESERVATION_KEY = "reservation_key"
        private const val FROM_KEY = "from_key"

        fun getIntent(
            context: Context,
            reservation: ListItem,
            fromMainScreen: Boolean = true,
        ): Intent =
            Intent(context, TicketingResultActivity::class.java)
                .putExtra(RESERVATION_KEY, reservation)
                .putExtra(FROM_KEY, fromMainScreen)
    }
}
