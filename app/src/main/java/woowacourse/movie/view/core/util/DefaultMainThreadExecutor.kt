package woowacourse.movie.view.core.util

import android.os.Handler
import android.os.Looper

/**
 * Android의 메인(UI) 스레드에서 코드를 실행해주는 기본 구현체
 *
 * Android에서는 UI 관련 작업(예: 텍스트 변경, 버튼 클릭 처리 등)은 반드시 메인 스레드(Main Thread)에서 실행 되어야 한다.
 * 이 클래스는 다른 스레드(예: 백그라운드 스레드)에서 전달받은 작업을 메인 스레드에서 안전하게 실행
 *
 * @property handler 메인 스레드와 연결된 Handler로, 작업을 메인 스레드의 메시지 큐에 전달
 *
 * Handler
 * - Handler는 Android에서 메시지나 Runnable(실행할 코드 블록)을 특정 스레드의 메시지 큐로 보내는 도구
 * - `Handler(Looper.getMainLooper())`는 메인 스레드에 연결된 메시지 큐에 작업을 전달
 * - 즉, `handler.post { ... }`를 호출하면, 그 코드 블록은 **메인 스레드에서 실행**됩니다.
 *
 */
class DefaultMainThreadExecutor : MainThreadExecutor {
    private val handler = Handler(Looper.getMainLooper())

    /**
     * 주어진 코드 블록을 메인(UI) 스레드에서 실행합니다.
     *
     * @param block 실행할 작업
     */
    override fun execute(block: () -> Unit) {
        handler.post(block)
    }
}
