package io.github.sgpublic.advcompose.core.window

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import io.github.sgpublic.advcompose.AdvComposeApplication
import io.github.sgpublic.advcompose.core.ComposeBase
import kotlin.reflect.KClass

abstract class ComposeWindow<Prop: WindowProp>: ComposeBase<Prop>() {
    private var created: Boolean = false
    @Composable
    private fun createWindow() {
        onComponentUpdate()
        prop.window.run window@{
            onCloseRequest = { AdvComposeApplication.closeWindow(this@ComposeWindow::class) }
            theme.run state@{
                Window(
                    onCloseRequest, state, visible, title, icon,
                    undecorated, resizable, enabled, focusable,
                    alwaysOnTop, onPreviewKeyEvent, onKeyEvent
                ) {
                    MaterialTheme(colors, typography, shapes) {
                        compose()
                    }
                }
            }
        }
        if (created){
            return
        }
        created = true
        onComponentCreated()
    }

    protected fun onClosing(): Boolean {
        return true
    }

    protected final fun close(){
        if (!onClosing()){
            return
        }
        AdvComposeApplication.closeWindow(this::class)
    }

    protected final fun finish(){
        AdvComposeApplication.finish()
    }

    companion object {
        @JvmStatic
        @Composable
        @Suppress("UNCHECKED_CAST")
        internal fun createWindow(windowClass: KClass<out ComposeWindow<out WindowProp>>, prop: WindowProp? = null) {
            windowClass.java.run {
                if (prop == null){
                    getDeclaredConstructor()
                        .newInstance()
                } else {
                    getDeclaredConstructor(prop.javaClass)
                        .newInstance(prop)
                }
            }.createWindow()
        }
    }
}