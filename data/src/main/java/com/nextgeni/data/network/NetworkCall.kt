package com.nextgeni.data.network

import android.util.Log
import com.nextgeni.data.util.TypeReference
import com.nextgeni.data.util.fromJson
import com.nextgeni.domain.exception.Exception404
import com.nextgeni.domain.exception.ServerException
import com.nextgeni.domain.exception.UnProcessableEntityException
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkCall @Inject constructor(
    var apiInterface: ApiInterface,
    var moshi: Moshi
) {

    suspend inline fun <reified T> generalRequest(
        crossinline request: suspend () -> Response<ResponseBody>
    ): Flow<T?> =
        flow {
            val response = request()
            if (response.isSuccessful) {
                val responseString = response.body()?.string()
                val type = object : TypeReference<T>() {}.type
                val model = moshi.fromJson<T>(responseString ?: "", type) as T?
                emit(model)
            } else {
                when (response.code()) {
                    422 -> {
                        val errorResponse = response.errorBody()?.string() ?: "{}"
                        throw UnProcessableEntityException(handle422Error(errorResponse))
                    }
                    500 -> {
                        throw ServerException()
                    }
                    400 -> {
                        throw Exception404()
                    }
                    else -> {
                        throw Exception("something went wrong")
                    }
                }
            }
        }

    @Throws(Exception::class)
    suspend inline fun <reified T> get(
        endpoint: String,
        queryMap: Map<String, String>? //query params
    ): Flow<T?> {

        return if (queryMap == null)
            generalRequest<T> { apiInterface.get(endpoint) }
        else
            generalRequest<T> { apiInterface.get(endpoint, queryMap) }
    }

    fun handle422Error(
        errorResponse: String
    ): HashMap<String, String> {
        val errorMap = HashMap<String, String>()

        var err: JSONObject = JSONObject(errorResponse)
        if (err.has("errors")) {
            err = err.getJSONObject("errors")
        }
        if (err.has("error")) {
            err = err.getJSONObject("error")
        }

        val keys = err.keys()
        while (keys.hasNext()) {
            var dynamicKey = keys.next().toString()
            try {
                val jsonArray = err.getJSONArray(dynamicKey)
                if (jsonArray.length() > 0) {
                    errorMap.put(dynamicKey, jsonArray.getString(0))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return errorMap
    }

    fun handleErrors(
        errorResponse: String
    ): String {
        Log.d("APP: error string ", errorResponse)
        var err: JSONObject = JSONObject(errorResponse)
        if (err.has("message"))
            return err.get("message").toString()

        if (err.has("error"))
            return err.get("error").toString()

        if (err.has("msg"))
            return err.get("msg").toString()

        return "Something went wrong"
    }



}