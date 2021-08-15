package io.github.sgpublic.advcompose.core.window

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.window.Window
import io.github.sgpublic.advcompose.AdvComposeApplication
import io.github.sgpublic.advcompose.core.ComposeBase
import io.github.sgpublic.advcompose.core.component.ComponentProp
import io.github.sgpublic.advcompose.core.component.ComposeComponent
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

    @Composable
    protected final fun includeComponent(clazz: KClass<out ComposeComponent<out ComponentProp>>){
        ComposeComponent.createComponent(clazz)
    }

    @ExperimentalComposeApi
    @Composable
    protected final fun <T: ComponentProp> includeComponent(clazz: KClass<out ComposeComponent<out T>>, prop: T){
//        ComposeComponent.createComponent(clazz, prop)
    }

    companion object {
        @JvmStatic
        @Composable
        internal fun createWindow(windowClass: KClass<out ComposeWindow<out WindowProp>>) {
            windowClass.java.getDeclaredConstructor()
                .newInstance().createWindow()
        }

        @JvmStatic
        @Composable
        @ExperimentalComposeApi
        internal fun <T: WindowProp> createWindow(windowClass: KClass<out ComposeWindow<T>>, prop: T) {
            windowClass.java.getDeclaredConstructor(prop.javaClass)
                .newInstance(prop).createWindow()
        }
    }
}