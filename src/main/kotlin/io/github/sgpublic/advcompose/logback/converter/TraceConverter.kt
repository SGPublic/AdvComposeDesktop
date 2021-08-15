package io.github.sgpublic.advcompose.logback.converter

import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent
import io.github.sgpublic.advcompose.util.Log

/** 日志定位 trace */
class TraceConverter: ClassicConverter() {
    override fun convert(event: ILoggingEvent): String {
        val judge = event.callerData[0].className.startsWith(Log::class.java.name)
        val callerData = if (judge){
            event.callerData[1]
        } else {
            event.callerData[0]
        }
        return "${callerData.fileName}:${callerData.lineNumber}"
    }
}