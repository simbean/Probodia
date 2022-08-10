package com.example.probodia.repository

import com.example.probodia.data.remote.api.RetrofitServerInstance
import com.example.probodia.data.remote.api.ServerService
import com.example.probodia.data.remote.body.GetApiTokenBody

class ServerRepository {

    private val client = RetrofitServerInstance.getInstance().create(ServerService::class.java)

    suspend fun getApiToken(userId : Long, accessToken: String)
        = client.getApiToken(GetApiTokenBody(userId, accessToken))

    suspend fun getTodayRecords(apiToken : String, page : Int, pageSize : Int)
        = client.getTodayRecord("Bearer ${apiToken}", page, pageSize)
}