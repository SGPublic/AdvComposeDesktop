package io.github.sgpublic.advcompose

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import io.github.sgpublic.advcompose.core.window.ComposeWindow
import io.github.sgpublic.advcompose.core.window.WindowProp
import com.sgpublic.ffmpgetool.core.util.ArgumentReader
import kotlin.reflect.KClass

abstract class AdvComposeApplication {
    companion object {
        private var debug = false
        @JvmStatic
        val DEBUG: Boolean get() = debug

        private var openedWindows: MutableMap<KClass<out ComposeWindow<out WindowProp>>, WindowProp?> = mutableStateMapOf()

        private var applicationStarted: Boolean = false
        private lateinit var exitApplication: () -> Unit

        @JvmStatic
        fun <T: ApplicationManifest> start(args: Array<String>, manifest: T) {
            setup(args)
            if (!applicationStarted) {
                applicationStarted = false
                openedWindows[manifest.mainWindow!!] = null
            }
            application {
                openedWindows = remember { openedWindows }
                exitApplication = ::exitApplication
                if (openedWindows.isEmpty()){
                    finish()
                }
                openedWindows.forEach { (key, value) ->
                    ComposeWindow.createWindow(key, value)
                }
            }
        }

        internal fun finish() {
            openedWindows.clear()
            exitApplication()
        }

        internal fun closeWindow(window: KClass<out ComposeWindow<out WindowProp>>){
            openedWindows.remove(window)
        }

        internal fun savedInstanceState(window: KClass<out ComposeWindow<out WindowProp>>,
                                        prop: WindowProp?){
            openedWindows[window] = prop
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