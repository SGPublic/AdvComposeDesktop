package io.github.sgpublic.advcompose

import io.github.sgpublic.advcompose.core.window.WindowProp
import io.github.sgpublic.advcompose.core.window.ComposeWindow
import kotlin.reflect.KClass

abstract class ApplicationManifest private constructor(){
    internal var mainWindow: KClass<out ComposeWindow<out WindowProp>>? = null
    internal var otherWindow: MutableList<KClass<out ComposeWindow<out WindowProp>>> = mutableListOf()

    fun setMainWindow(clazz: KClass<out ComposeWindow<out WindowProp>>): ApplicationManifest {
        mainWindow = clazz
        return this
    }

    fun addSecondaryWindow(clazz: KClass<out ComposeWindow<out WindowProp>>): ApplicationManifest {
        otherWindow += clazz
        return this
    }

    companion object {
        @JvmStatic
        fun newInterface(): ApplicationManifest {
            return object : ApplicationManifest() { }
        }
    }
}