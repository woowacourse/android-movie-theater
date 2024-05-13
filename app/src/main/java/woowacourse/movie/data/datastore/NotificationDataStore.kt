package woowacourse.movie.data.datastore

interface NotificationDataStore {
    var acceptedPushAlarm: Boolean
    var hasBeenDeniedPermission: Boolean
}
