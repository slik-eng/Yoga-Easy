package com.slikeng.yogaeasytest.network

import androidx.annotation.Keep
import com.slikeng.yogaeasytest.util.Constants
import com.squareup.moshi.Json
import io.reactivex.Observable
import retrofit2.http.GET

internal interface MainApi {
    @GET(Constants.BATH)
    fun requestObjList(): Observable<ResponseList>
}

/**
 * The class represents the response object returned by the [MainApi.requestObjList] api.
 */
@Keep
internal data class ResponseList(
    @Json(name = "products")
    val products: List<ObjItem>,
)
@Keep
internal data class ObjItem(
    @Json(name = "id")
    val id: Int,
    @Json(name = "apple_product_id")
    val appleProductId : String,
    @Json(name = "name")
    val name : String,
    @Json(name = "description_html")
    val description_html : String,
    @Json(name = "subscription_cycle_length")
    val subscription_cycle_length : Long,
    @Json(name = "google_subscription_id")
    val google_subscription_id : String
)