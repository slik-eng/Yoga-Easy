package com.slikeng.yogaeasytest

import com.slikeng.yogaeasytest.network.ResponseList
import com.slikeng.yogaeasytest.util.Async
import com.slikeng.yogaeasytest.util.doOnAsyncFailure
import com.slikeng.yogaeasytest.util.doOnAsyncSuccess
import com.slikeng.yogaeasytest.util.mapAsyncSuccessSafely
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign

internal class MainActivityViewModel(private val interactor: MainActivityInteractor) :
    BaseViewModel<MainActivityAction, MainActivityViewState, MainActivityEffect>(
        initialState()
    ) {

    override fun handleActions(actions: Observable<MainActivityAction>) {
        disposables += actions
            .subscribe {
                handleAction(it)
            }
    }

    private fun handleAction(action: MainActivityAction) {
        when (action) {
            is MainActivityAction.CallData -> requestObjList()
            is MainActivityAction.ItemClicked -> sendEffect(MainActivityEffect.ItemClicked(action.objItem))
        }
    }

    private fun requestObjList() {
        disposables += interactor.requestObjList()
            .mapAsyncSuccessSafely { arrayList: ResponseList -> arrayList }
            .doOnAsyncSuccess {
                updateState {
                    copy(
                        isShowingProgressBar = false
                    )
                }
            }
            .doOnAsyncFailure {
                updateState {
                    copy(
                        isShowingProgressBar = false
                    )
                }
            }
            .subscribe {
                when (it) {
                    is Async.Success -> {
                        updateState {
                            copy(
                                isShowingInputError = false,
                                isShowingProgressBar = false,
                                isShowingRecyclerView = true
                            )
                        }
                        sendEffect(
                        MainActivityEffect.GetObjList(it.data)
                    )
                    }
                    is Async.Failure ->
                        updateState {
                            copy(
                                isShowingInputError = true,
                                inputErrorRes = R.string.no_data,
                                isShowingProgressBar = false
                            )
                        }
                    is Async.Running ->
                        updateState {
                            copy(
                                isShowingProgressBar = true,
                                isShowingInputError = false,
                                isShowingRecyclerView = false
                            )
                        }
                }
            }
    }
}

private fun initialState(): MainActivityViewState {
    return MainActivityViewState(
        inputErrorRes = null,
        isShowingInputError = false
    )
}