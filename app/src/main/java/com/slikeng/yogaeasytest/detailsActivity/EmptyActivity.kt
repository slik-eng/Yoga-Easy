package com.slikeng.yogaeasytest.detailsActivity

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import androidx.appcompat.app.AppCompatActivity
import com.slikeng.yogaeasytest.R
import kotlinx.android.synthetic.main.activity_empty.*

class EmptyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
        obj_details.text = getDataFromIntent()
    }

    private fun getDataFromIntent(): String {
        return intent.getIntExtra(OBJ_ID, 0).toString().plus("\n").plus(
            intent.getStringExtra(OBJ_NAME)
        ).plus("\n").plus(getHTMLText())
    }

    private fun getHTMLText(): Spanned {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(intent.getStringExtra(OBJ_DESCRIPTION), Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(intent.getStringExtra(OBJ_DESCRIPTION))
        }
    }

    companion object {
        internal const val OBJ_ID = "com.slikeng.yogaeasytest.OBJ_ID"
        internal const val OBJ_NAME = "com.slikeng.yogaeasytest.OBJ_NAME"
        internal const val OBJ_DESCRIPTION = "com.slikeng.yogaeasytest.OBJ_DESCRIPTION"
    }
}