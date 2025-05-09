package woowacourse.movie.view.reservation.detail.viewhelper

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.R.layout
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.view.reservation.detail.ReservationDetailActivity
import java.time.LocalDate

class DateTimeHelper(
    private val activity: ReservationDetailActivity,
    private val binding: ActivityReservationBinding,
) {
    fun updateDateAdapter(
        duration: List<LocalDate>,
        selected: Int,
    ) {
        val dateAdapter =
            ArrayAdapter(
                activity,
                layout.support_simple_spinner_dropdown_item,
                duration,
            )

        binding.spinnerReservationDate.apply {
            adapter = dateAdapter
            setSelection(selected)
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long,
                    ) {
                        activity.presenter.selectDate(duration[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
        }
    }

    fun updateTimeAdapter(times: List<String>) {
        val timeAdapter =
            ArrayAdapter(
                activity,
                layout.support_simple_spinner_dropdown_item,
                times,
            )

        binding.spinnerReservationTime.apply {
            adapter = timeAdapter
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long,
                    ) {
                        activity.presenter.selectTime(position)
                        activity.presenter.isTimeSelected = true
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
        }
    }
}
