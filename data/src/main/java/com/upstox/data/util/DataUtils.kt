package com.upstox.data.util

import androidx.annotation.Keep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

inline fun <T> resourceFlow(
    crossinline block: suspend () -> T
): Flow<Resource<T>> {
    return flow { emit(Resource.success(block.invoke())) }
        .onStart { emit(Resource.loading()) }
        .catch { emit(Resource.error(it.getErrorMessage())) }
}

@Keep
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {

    companion object {

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

    }

}

@Keep
enum class Status {
    LOADING, SUCCESS, ERROR
}

fun Throwable.getErrorMessage(): String? {
    return try {
        when (this) {
            is IOException -> {
                println(this.printStackTrace())
                "Please check your internet connection"
            }
            is HttpException -> {
                // Handling most common APIs error message pattern
                val errorBody = response()?.errorBody()?.string()
                val json = JSONObject(errorBody ?: return null)
                return try {
                    json.getString("message")
                } catch (e: Exception) {
                    try {
                        json.getString("error")
                    }catch (e:Exception){
                        val jsonData = JSONObject(json.getString("data").orEmpty())
                        jsonData.getString("message")
                    }
                }
            }
            else -> message
        }
    } catch (e: Exception) {
        message
    }
}
