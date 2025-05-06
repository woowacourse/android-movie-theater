package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Seat(
    val label: String,
) : Parcelable {
    @IgnoredOnParcel
    val rowLabel: String by lazy { label.substring(0, 1).uppercase() }

    val price: Int
        get() = SeatGrade.getSeatGrade(this).price

    init {
        require(rowLabel.matches(Regex("[A-Z]"))) { "유효하지 않은 행 라벨 입니다. 알파벳 대문자를 사용하세요. 현재 값: $rowLabel" }
    }
}
