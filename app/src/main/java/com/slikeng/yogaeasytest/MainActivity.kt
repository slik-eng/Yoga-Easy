package com.slikeng.yogaeasytest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.slikeng.yogaeasytest.adapter.ObjListAdapter
import com.slikeng.yogaeasytest.detailsActivity.EmptyActivity
import com.slikeng.yogaeasytest.detailsActivity.EmptyActivity.Companion.OBJ_DESCRIPTION
import com.slikeng.yogaeasytest.detailsActivity.EmptyActivity.Companion.OBJ_ID
import com.slikeng.yogaeasytest.detailsActivity.EmptyActivity.Companion.OBJ_NAME
import com.slikeng.yogaeasytest.util.CompositeLifecycleDisposable
import com.slikeng.yogaeasytest.util.plusAssign
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by inject()
    private val disposable = CompositeLifecycleDisposable(this)
    private val actions = PublishSubject.create<MainActivityAction>()
    private var objListAdapter = ObjListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposable += viewModel.states().subscribe { state -> renderState(state) }
        disposable += viewModel.effects().subscribe { effect -> handleEffect(effect) }
        viewModel.attach(actions())

        actions.onNext(MainActivityAction.CallData)
        setListeners()
    }

    private fun actions(): Observable<MainActivityAction> = Observable.merge(
        objListAdapter.actions(),
        actions
    )

    private fun renderState(viewState: MainActivityViewState) {
        progress_bar.isVisible = viewState.isShowingProgressBar
        recycler_view.isVisible = viewState.isShowingRecyclerView
        text_error.isVisible = viewState.isShowingInputError
        viewState.inputErrorRes?.let { text_error.setText(it) }
    }

    private fun initRecycleViews() {
        recycler_view.isNestedScrollingEnabled = false
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = objListAdapter
        viewModel.attach(actions())
    }

    private fun handleEffect(effect: MainActivityEffect) {
        when(effect) {
            is MainActivityEffect.GetObjList -> {
                objListAdapter = ObjListAdapter(effect.list.products)
                initRecycleViews()
            }
            is MainActivityEffect.ItemClicked -> startActivity(makeIntent(this, effect.objItem.id, effect.objItem.name, effect.objItem.description_html))
        }
    }

    private fun setListeners() {
        text_error.setOnClickListener {
            actions.onNext(MainActivityAction.CallData)
        }
    }

    private fun makeIntent(context: Context, id: Int, name: String, description: String) =
        Intent(context, EmptyActivity::class.java)
            .putExtra(OBJ_ID, id)
            .putExtra(OBJ_NAME, name)
            .putExtra(OBJ_DESCRIPTION, description)

    override fun onDestroy() {
        viewModel.detach()
        disposable.dispose()
        super.onDestroy()
    }
}