package com.slikeng.yogaeasytest.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slikeng.yogaeasytest.MainActivityAction
import com.slikeng.yogaeasytest.R
import com.slikeng.yogaeasytest.network.ObjItem
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_view.view.*

internal class ObjListAdapter(
    objList: List<ObjItem>
): RecyclerView.Adapter<ObjListAdapter.ObjListItemViewHolder>() {

    private val actions = PublishSubject.create<MainActivityAction>()
    private val disposables = CompositeDisposable()
    private val objList = ArrayList<ObjItem>(objList)

    inner class ObjListItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        internal fun bind(objItem: ObjItem) {
            view.package_id.text = objItem.id.toString().plus("- ")
            view.package_name.text = objItem.name
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                view.description.text = Html.fromHtml(objItem.description_html, Html.FROM_HTML_MODE_COMPACT)
            } else {
                view.description.text = Html.fromHtml(objItem.description_html)
            }
            view.setOnClickListener {
                actions.onNext(MainActivityAction.ItemClicked(objItem))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ObjListItemViewHolder(view)
    }

    override fun getItemCount() = objList.size

    override fun onBindViewHolder(holder: ObjListItemViewHolder, position: Int) {
        holder.bind(objList[position])
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        disposables.dispose()
    }

    fun actions(): Observable<MainActivityAction> = actions

}