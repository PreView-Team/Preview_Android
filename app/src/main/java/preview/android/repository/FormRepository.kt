package preview.android.repository

import android.util.Log
import kotlinx.coroutines.flow.callbackFlow
import preview.android.activity.api.FormService
import preview.android.model.EditForm
import preview.android.model.Form

class FormRepository(private val api : FormService) {

    suspend fun sendForm(token: String, form: Form) = callbackFlow {
        val request = api.createFrom("Bearer $token", form)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            Log.e("sendForm ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun getSendForms(token: String) = callbackFlow {
        val request = api.getAllMyForm("Bearer $token")
        if(request.isSuccessful && request.body() != null){
            trySend(request.body()!!)
        }
        else{
            Log.e("getSendForms ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun getReceiveForms(token: String) = callbackFlow {
        val request = api.getAllReceiveForm("Bearer $token")
        if(request.isSuccessful && request.body() != null){
            trySend(request.body()!!)
        }
        else{
            Log.e("getReceiveForms ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun getFormDetail(token: String, formId: Int) = callbackFlow {
        val request = api.getFormDetail("Bearer $token", formId)
        if(request.isSuccessful && request.body() != null){
            trySend(request.body()!!)
        }
        else{
            Log.e("getFormDetail ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun getReceiveFormDetail(token: String, formId: Int) = callbackFlow {
        val request = api.getReceiveFormDetail("Bearer $token", formId)
        if(request.isSuccessful && request.body() != null){
            trySend(request.body()!!)
        }
        else{
            Log.e("getFormDetail ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun deleteForm(token: String, formId: Int) = callbackFlow {
        val request = api.deleteForm("Bearer $token", formId)
        if(request.isSuccessful && request.body() != null){
            trySend(request.body()!!)
        }
        else{
            Log.e("deleteForm ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun editForm(token: String, formId : Int,editForm : EditForm) = callbackFlow {
        val request = api.editForm("Bearer $token", formId, editForm)
        if(request.isSuccessful && request.body() != null){
            trySend(request.body()!!)
        }
        else{
            Log.e("deleteForm ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }
}