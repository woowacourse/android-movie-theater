package domain

data class TicketCount(
    var numberOfPeople: Int = MIN_BOOKER_NUMBER,
) {
    init {
        require(numberOfPeople in MIN_BOOKER_NUMBER..MAX_BOOKER_NUMBER) { RANGE_ERROR_MESSAGE }
    }

    fun increase() {
        numberOfPeople++
    }


    fun decrease(){
        numberOfPeople--
    }

    companion object {
        private const val MIN_BOOKER_NUMBER = 1
        private const val MAX_BOOKER_NUMBER = 10
        private const val RANGE_ERROR_MESSAGE = "예약자 수는 최소 ${MIN_BOOKER_NUMBER}명 최대 ${MAX_BOOKER_NUMBER}명까지 가능합니다."
    }
}
