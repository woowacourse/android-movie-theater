package woowacourse.movie.notification

enum class NotificationDialogInfo(val title: String, val text: String) {
    RemindingBookingTime(
        title = "영화 시간 알림",
        text = "상영 시작까지 30분 남았습니다."
    );
}
