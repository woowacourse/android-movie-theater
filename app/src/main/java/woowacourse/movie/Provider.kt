package woowacourse.movie

import android.content.Context
import androidx.fragment.app.Fragment
import woowacourse.movie.data.MovieTheaterDatabase
import woowacourse.movie.repository.SettingRepository
import woowacourse.movie.repository.TicketRepository
import woowacourse.movie.view.movies.reservation.seat.SeatSelectionContract
import woowacourse.movie.view.movies.reservation.seat.SeatSelectionPresenter
import woowacourse.movie.view.reservelist.ReservationListContract
import woowacourse.movie.view.reservelist.ReservationListPresenter
import woowacourse.movie.view.setting.SettingContract
import woowacourse.movie.view.setting.SettingPresenter

object Provider {
    fun ticketDao(context: Context) = MovieTheaterDatabase.db(context).ticketDao()

    fun ticketRepository(context: Context): TicketRepository =
        TicketRepository(
            ticketDao(context),
        )

    fun settingRepository(context: Context): SettingRepository =
        SettingRepository(
            context.getSharedPreferences("setting", Context.MODE_PRIVATE),
        )

    fun <T> reservationListPresenter(view: T) where T : ReservationListContract.View, T : Fragment =
        ReservationListPresenter(
            view,
            ticketRepository(view.requireContext().applicationContext),
        )

    fun <T> seatSelectionPresenter(view: T) where T : SeatSelectionContract.View, T : Context =
        SeatSelectionPresenter(view, ticketRepository(view.applicationContext))

    fun <T> settingPresenter(view: T) where T : SettingContract.View, T : Fragment =
        SettingPresenter(
            view,
            ticketRepository(view.requireContext().applicationContext),
            settingRepository(view.requireContext().applicationContext),
        )
}
