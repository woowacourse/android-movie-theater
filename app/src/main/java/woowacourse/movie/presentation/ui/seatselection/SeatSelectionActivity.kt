package woowacourse.movie.presentation.ui.seatselection

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import woowacourse.movie.R
import woowacourse.movie.data.repository.ReservationRepositoryImpl
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.domain.dummy.DummyScreens
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.notification.Notification
import woowacourse.movie.presentation.base.BaseMvpBindingActivity
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.model.UserSeat
import woowacourse.movie.presentation.ui.reservation.ReservationActivity
import woowacourse.movie.presentation.ui.seatselection.SeatSelectionContract.View
import java.io.Serializable

class SeatSelectionActivity : BaseMvpBindingActivity<ActivitySeatSelectionBinding>(), View {
    override val layoutResourceId: Int
        get() = R.layout.activity_seat_selection
    override val presenter: SeatSelectionPresenter by lazy {
        SeatSelectionPresenter(
            this,
            DummyScreens,
            ReservationRepositoryImpl(applicationContext),
            Notification(applicationContext),
        )
    }

    override fun initStartView() {
        binding.presenter = presenter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val reservationInfo =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra(
                    PUT_EXTRA_KEY_RESERVATION_INFO,
                    ReservationInfo::class.java,
                )
            } else {
                intent.getSerializableExtra(PUT_EXTRA_KEY_RESERVATION_INFO) as ReservationInfo
            }

        reservationInfo?.let { reservationInfoItem ->
            presenter.updateUiModel(reservationInfoItem)
            presenter.loadScreen(reservationInfoItem.theaterId, reservationInfoItem.movieId)
            presenter.loadSeatBoard(reservationInfoItem.theaterId)
        }
    }

    override fun showScreen(
        screen: Screen,
        totalPrice: Int,
        ticketCount: Int,
    ) {
        binding.screen = screen
        binding.totalPrice = totalPrice
        binding.ticketCount = ticketCount
    }

    override fun showSeatBoard(userSeat: UserSeat) {
        binding.userSeat = userSeat
    }

    override fun selectSeat(userSeat: UserSeat) {
        binding.userSeat = userSeat
        presenter.calculateSeat()
    }

    override fun unselectSeat(userSeat: UserSeat) {
        binding.userSeat = userSeat
        presenter.calculateSeat()
    }

    override fun showTotalPrice(totalPrice: Int) {
        binding.totalPrice = totalPrice
    }

    override fun showReservationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.dialog_reservation_title))
        builder.setMessage(getString(R.string.dialog_reservation_message))
        builder.setCancelable(false)

        builder.setPositiveButton(getString(R.string.reservation_done)) { _, _ ->
            presenter.reserve()
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    override fun navigateToReservation(id: Long) {
        ReservationActivity.startActivity(this, id)
        navigateBackToPrevious()
    }

    override fun navigateBackToPrevious() = finish()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navigateBackToPrevious()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(PUT_STATE_KEY_USER_SEAT, presenter.userSeat)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val savedUserSeat = savedInstanceState.getSerializable(PUT_STATE_KEY_USER_SEAT) as UserSeat?
        savedUserSeat?.let { userSeat ->
            userSeat.seatModels.forEach { seat ->
                val n = seat.copy(isSelected = false)
                presenter.clickSeat(n)
            }
        }
    }

    companion object {
        private const val PUT_EXTRA_KEY_RESERVATION_INFO = "reservationInfo"
        private const val PUT_STATE_KEY_USER_SEAT = "userSeat"

        fun startActivity(
            context: Context,
            reservationInfo: ReservationInfo,
        ) {
            val intent = Intent(context, SeatSelectionActivity::class.java)
            intent.putExtra(PUT_EXTRA_KEY_RESERVATION_INFO, reservationInfo as Serializable)
            context.startActivity(intent)
        }
    }
}
