package io.github.sgpublic.advcompose.core.exception

class AdvComposeRuntimeException(override val message: String?): Exception() {
    companion object {
        internal val NO_MAIN_WINDOW: AdvComposeRuntimeException
            get() = AdvComposeRuntimeException("请至少设置一个 @MainWindow")
        internal val MULTI_MAIN_WINDOW: AdvComposeRuntimeException
            get() = AdvComposeRuntimeException("请勿设置多个 @MainWindow")
        internal val WINDOW_EXTENDS: AdvComposeRuntimeException
            get() = AdvComposeRuntimeException("请将 @MainWindow 的 class 继承自 ComponentWindow<out WindowProp> 或 SampleComponentWindow")
        internal val MULTI_COMPOSABLE: AdvComposeRuntimeException
            get() = AdvComposeRuntimeException("请勿在同一个 class 中设置多个 @AdvComposable")
    }
}