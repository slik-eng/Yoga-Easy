package com.slikeng.yogaeasytest

import com.slikeng.yogaeasytest.network.ObjItem
import com.slikeng.yogaeasytest.network.ResponseList

internal sealed class MainActivityEffect : ViewModelEffect{
    data class GetObjList(val list: ResponseList) : MainActivityEffect()
    data class ItemClicked(val objItem: ObjItem) : MainActivityEffect()
}