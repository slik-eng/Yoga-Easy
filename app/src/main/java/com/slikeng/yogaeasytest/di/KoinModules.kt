package com.slikeng.yogaeasytest.di

import org.koin.core.module.Module

fun buildKoinModules() : List<Module> {
    return listOf(
        mainActivityKoinModule
    )
}