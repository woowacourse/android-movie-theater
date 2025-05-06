package woowacourse.movie.ui.booking.view.spinner.listener

import android.view.View
import android.widget.AdapterView
import java.time.LocalTime

class ScreeningTimeListener(
    val onSelectTime: (LocalTime) -> Unit
) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onSelectTime(parent?.getItemAtPosition(position) as LocalTime)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        onSelectTime(parent?.getItemAtPosition(0) as LocalTime)
    }

}