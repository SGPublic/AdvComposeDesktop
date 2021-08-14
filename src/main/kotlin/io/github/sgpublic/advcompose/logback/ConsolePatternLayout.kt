package io.github.sgpublic.advcompose.logback

import ch.qos.logback.classic.PatternLayout
import io.github.sgpublic.advcompose.logback.converter.TraceConverter

/**
 * 添加自定义参数
 */
class ConsolePatternLayout: PatternLayout() {
    companion object {
        init {
            defaultConverterMap["trace"] = TraceConverter::class.java.name
        }
    }
}