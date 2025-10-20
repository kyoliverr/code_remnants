package com.example.buscalog.function.task

import com.example.buscalog.data.TaskFields
import com.example.buscalog.data.UpdateTask
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class TaskSerializer : JsonSerializer<UpdateTask>{
    override fun serialize(
        src: UpdateTask,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val obj = JsonObject()

        obj.addProperty("datetime", src.datetime)
        obj.addProperty("longitude", src.longitude)
        obj.addProperty("latitude", src.latitude)

        val gson = Gson()
        val fieldsJson = gson.toJson(src.fields ?: emptyList<TaskFields>())
        obj.addProperty("fields", fieldsJson)

        return obj
    }
}
