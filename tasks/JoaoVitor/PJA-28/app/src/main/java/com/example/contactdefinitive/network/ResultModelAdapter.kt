package com.example.contactdefinitive.network

import com.example.contactdefinitive.ResultModel
import com.example.contactdefinitive.askValue
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ResultModelAdapter {
    @FromJson
    fun fromJson(json: Map<String, askValue>): ResultModel {
        return ResultModel(currencies = json)
    }

    @ToJson
    fun toJson(resultModel: ResultModel): Map<String, askValue> {
        return resultModel.currencies
    }
}
