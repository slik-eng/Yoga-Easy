package com.slikeng.yogaeasytest

import com.slikeng.yogaeasytest.network.ObjItem

internal sealed class MainActivityAction : ViewModelAction {
    data class ItemClicked(val objItem: ObjItem) : MainActivityAction()
    object CallData : MainActivityAction()

}