package woowacourse.movie.view.main.reservationlist

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import woowacourse.movie.database.AppDatabase
import woowacourse.movie.model.reservation.ReservationInfo

class ReservationListPresenter(
    private var view: ReservationListContract.View?,
    private val context: Context,
) : ReservationListContract.Presenter {
    private val reservationInfos = mutableListOf<ReservationInfo>()

    override fun onViewCreated() {
        loadReservationInfos()
    }

    override fun onDestroyView() {
        view = null
    }

    override fun loadReservationInfos() {
        Thread {
            reservationInfos.clear()
            reservationInfos.addAll(
                AppDatabase
                    .getDatabase(context)
                    .reservationInfoDao()
                    .getAllReservations(),
            )

            if (reservationInfos.isNotEmpty()) {
                Log.d("ReservationListPresenter", "reservationInfos: $reservationInfos")
                Handler(Looper.getMainLooper()).post {
                    view?.showReservationInfos(reservationInfos)
                }
            }
        }.start()
    }

    override fun getReservationInfos(): List<ReservationInfo> = reservationInfos

    override fun onReservationItemClicked(position: Int) {
        TODO("예약 완료 액티비티로 이동")
    }
}
