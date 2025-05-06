package woowacourse.movie.ui.booking.view.spinner.listener

import android.view.View
import android.widget.AdapterView
import java.time.LocalDate

class ScreeningDateListener(
    val onSelectDate: (LocalDate) -> Unit,
) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long,
    ) {
        onSelectDate(parent?.getItemAtPosition(position) as LocalDate)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        onSelectDate(parent?.getItemAtPosition(0) as LocalDate)
    }
}
