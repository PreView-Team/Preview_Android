package preview.android.activity.api

import com.google.gson.JsonArray
import preview.android.activity.api.dto.FormDetailResponse
import preview.android.activity.api.dto.FormResponse
import preview.android.activity.api.dto.ReceiveFormDetailResponse
import preview.android.model.EditForm
import preview.android.model.Form
import retrofit2.Response
import retrofit2.http.*

interface FormService {

    @POST("/api/form")
    suspend fun createFrom(
        @Header("Authorization") token: String,
        @Body fom: Form
    ): Response<FormResponse>

    @GET("/api/form")
    suspend fun getAllMyForm(
        @Header("Authorization") token: String
    ): Response<JsonArray>

    @GET("/api/mentor/form")
    suspend fun getAllReceiveForm(
        @Header("Authorization") token: String
    ): Response<JsonArray>

    @GET("/api/form/{formId}")
    suspend fun getFormDetail(
        @Header("Authorization") token: String,
        @Path("formId") formId: Int
    ): Response<FormDetailResponse>

    @GET("/api/mentor/form/{formId}")
    suspend fun getReceiveFormDetail(
        @Header("Authorization") token: String,
        @Path("formId") formId: Int
    ): Response<ReceiveFormDetailResponse>

    @DELETE("/api/form/{formId}")
    suspend fun deleteForm(
        @Header("Authorization") token: String,
        @Path("formId") formId: Int
    ): Response<FormResponse>

    @PUT("/api/form/{formId}")
    suspend fun editForm(
        @Header("Authorization") token: String,
        @Path("formId") formId: Int,
        @Body editForm: EditForm
    ): Response<FormResponse>

    @POST("/api/mentor/form/{formId}")
    suspend fun acceptForm(
        @Header("Authorization") token: String,
        @Path("formId") formId: Int,
    ) : Response<FormResponse>

    @DELETE("/api/mentor/form/{formId}")
    suspend fun refuseForm(
        @Header("Authorization") token: String,
        @Path("formId") formId: Int,
    ) : Response<FormResponse>

}