package com.m4ykey.stos

private const val DESKTOP_KEY_NAME = "APP_API_KEY"

object KProvider {
    val API_KEY : String by lazy {
        System.getProperty(DESKTOP_KEY_NAME)
            ?: throw IllegalStateException("Api key is not set. Use -D$DESKTOP_KEY_NAME=<key> in jvmArgs.")
    }
}