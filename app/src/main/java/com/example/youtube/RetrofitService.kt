package com.example.youtube

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.Serializable

class StudentFromServer(
    val id: Int,
    val name: String,
    val age: Int,
    val intro: String
){
    constructor(name: String, age: Int, intro: String): this(0, name, age, intro)
}

interface RetrofitService {
    @GET("melon/list/")
    fun getMelonItemList(): Call<ArrayList<MelonItem>>

    @GET("json/students/")
    fun getStudentList(): Call<ArrayList<StudentFromServer>>

    @GET("youtube/list/")
    fun getYoutubeItemList(): Call<ArrayList<YoutubeItem>>

    @POST("json/students/")
    fun createStudent(
        @Body params: HashMap<String, Any>
    ): Call<StudentFromServer>

    @POST("json/students/")
    fun easyCreateStudent(
        @Body student: StudentFromServer
    ): Call<StudentFromServer>

}

class YoutubeItem(
    val id: Int, val title: String, val content: String, val video: String, val thumbnail: String
)

class MelonItem(
    val id: Int, val title: String, val song: String, val thumbnail: String
): Serializable