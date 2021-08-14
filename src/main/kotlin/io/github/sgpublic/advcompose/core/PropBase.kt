package io.github.sgpublic.advcompose.core

abstract class PropBase {
    final override fun toString(): String {
        return this.javaClass.name
    }
}