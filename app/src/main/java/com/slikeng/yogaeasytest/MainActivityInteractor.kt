package com.slikeng.yogaeasytest

import com.slikeng.yogaeasytest.network.MainApi
import com.slikeng.yogaeasytest.network.ObjItem
import com.slikeng.yogaeasytest.network.ResponseList
import com.slikeng.yogaeasytest.util.Async
import com.slikeng.yogaeasytest.util.mapToAsync
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

internal class MainActivityInteractor (private val mainApi: MainApi) {
    fun requestObjList() : Observable<Async<ResponseList>> = mainApi.requestObjList().subscribeOn(Schedulers.io()).mapToAsync().startWith(Async.Running())
}