package woowacourse.movie.presentation.activities.ticketing.listener

import android.view.View
import android.widget.AdapterView

class OnSpinnerItemSelectedListener(private val onClick: (Int) -> Unit) :
    AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onClick(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}
