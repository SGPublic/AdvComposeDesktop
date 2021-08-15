package io.github.sgpublic.advcompose

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import io.github.sgpublic.advcompose.util.ArgumentReader
import io.github.sgpublic.advcompose.util.Log
import io.github.sgpublic.advcompose.classic.component.window.SampleWindow
import io.github.sgpublic.advcompose.core.annotation.MainWindow
import io.github.sgpublic.advcompose.core.exception.AdvComposeRuntimeException
import io.github.sgpublic.advcompose.core.window.ComposeWindow
import io.github.sgpublic.advcompose.core.window.WindowProp
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import kotlin.reflect.KClass

abstract class AdvComposeApplication {
    companion object {
        private var debug = false
        @JvmStatic
        val DEBUG: Boolean get() = debug

        private var openedWindows: MutableList<KClass<out ComposeWindow<out WindowProp>>> = mutableStateListOf()

        private lateinit var mainWindow: KClass<out ComposeWindow<out WindowProp>>
        private lateinit var otherWindow: List<KClass<out ComposeWindow<out WindowProp>>>

        private var applicationStarted: Boolean = false
        private lateinit var exitApplication: () -> Unit

        @JvmStatic
        fun start(args: Array<String>) {
            setup(args)
            scanWindow()
            if (!applicationStarted) {
                applicationStarted = false
                openedWindows += mainWindow
            }
            application {
                openedWindows = remember { openedWindows }
                exitApplication = ::exitApplication
                if (openedWindows.isEmpty()){
                    finish()
                }
                openedWindows.forEach {
                    ComposeWindow.createWindow(it)
                }
            }
        }

        private fun scanWindow(){
            val packageName = Throwable().stackTrace[2].className.run {
                if (!this.contains(".")){
                    return@run ""
                } else {
                    return@run substring(0, lastIndexOf("."))
                }
            }
            Log.d("开始扫描 ComposeWindow：$packageName")
            val windows = Reflections(packageName, TypeAnnotationsScanner())
                .getTypesAnnotatedWith(MainWindow::class.java, true)
            if (windows.isEmpty()){
                Log.w("未设置 @MainWindow，使用 io.github.sgpublic.advcompose.classic.component.window.SampleWindow")
                this.mainWindow = SampleWindow::class
            } else {
                if (windows.size != 1){
                    throw AdvComposeRuntimeException.MULTI_MAIN_WINDOW
                }
                val mainWindow = ArrayList(windows)[0]
                if (!ComposeWindow::class.java.isAssignableFrom(mainWindow)){
                    Log.e("错误的继承关系：${mainWindow.name}")
                    throw AdvComposeRuntimeException.WINDOW_EXTENDS
                }
                @Suppress("UNCHECKED_CAST")
                this.mainWindow = mainWindow.kotlin as KClass<out ComposeWindow<out WindowProp>>
            }
            val otherWindow = arrayListOf<KClass<out ComposeWindow<out WindowProp>>>()
            Reflections(packageName, SubTypesScanner(false)).getSubTypesOf(ComposeWindow::class.java).forEach {
                otherWindow.add(it.kotlin as KClass<out ComposeWindow<out WindowProp>>)
            }
            this.otherWindow = otherWindow
        }

        internal fun finish() {
            openedWindows.clear()
            exitApplication()
        }

        internal fun closeWindow(window: KClass<out ComposeWindow<out WindowProp>>){
            openedWindows.remove(window)
        }

        @ExperimentalComposeApi
        internal fun savedInstanceState(window: KClass<out ComposeWindow<out WindowProp>>,
                                        prop: WindowProp?){
//            openedWindows[window] = prop
        }

        private fun setup(args: Array<String>): Array<String> {
            val argsCurrent = arrayListOf<String>()
            argsCurrent.addAll(args)
            val reader = ArgumentReader(args)
            debug = reader.containsItem("--debug")
            return Array(argsCurrent.size) {
                return@Array argsCurrent[it]
            }
        }
    }
}