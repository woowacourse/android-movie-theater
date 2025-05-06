package woowacourse.movie.view.reservation

import android.content.Context
import android.widget.ArrayAdapter
import java.time.LocalTime

class TimeSpinnerAdapter(
    context: Context,
    times: List<LocalTime> = emptyList(),
) : ArrayAdapter<LocalTime>(
        context,
        com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
        times,
    ) {
    override fun getItem(position: Int): LocalTime? = super.getItem(position)

    fun updateDateItems(newItems: List<LocalTime>) {
        clear()
        addAll(newItems)
        notifyDataSetChanged()
    }
}
