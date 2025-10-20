package com.example.buscalog.function.task

import android.util.Log
import com.example.buscalog.data.Task
import com.example.buscalog.data.TaskEnterprise
import com.example.buscalog.data.TaskFields
import com.example.buscalog.data.TaskLocation
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import java.lang.reflect.Type

class TaskDeserializer : JsonDeserializer<Task> {
    override fun deserialize(
        json: JsonElement, typeOfT: Type, context: JsonDeserializationContext
    ): Task {
        val obj = json.asJsonObject

        val id: Int = context.deserialize(obj.get("id"), Int::class.java)
        val name: String = context.deserialize(obj.get("name"), String::class.java)
        val description: String? = context.deserialize(obj.get("description"), String::class.java)
        val status: String = context.deserialize(obj.get("status"), String::class.java)
        val beginDatetime: String? = context.deserialize(obj.get("begin_datetime"), String::class.java)
        val endDatetime: String? = context.deserialize(obj.get("end_datetime"), String::class.java)
        val startDatetime: String? = context.deserialize(obj.get("start_datetime"), String::class.java)
        val finishDatetime: String? = context.deserialize(obj.get("finish_datetime"), String::class.java)

        val enterpriseObj = obj.getAsJsonObject("enterprise")
        val enterprise = TaskEnterprise(
            enterpriseObj.get("id").asInt, enterpriseObj.get("name").asString
        )
        val startLocation: TaskLocation? = if (obj.has("start_location") && !obj.get("start_location").isJsonNull) {
            val loc = obj.getAsJsonObject("start_location")
            TaskLocation(
                loc.get("id")?.asInt, loc.get("latitude")?.asDouble, loc.get("longitude")?.asDouble
            )
        } else null
        val location: TaskLocation? = if (obj.has("location") && !obj.get("location").isJsonNull) {
            val loc = obj.getAsJsonObject("location")
            TaskLocation(
                loc.get("id")?.asInt, loc.get("latitude")?.asDouble, loc.get("longitude")?.asDouble
            )
        } else null

        val fieldsList = ArrayList<TaskFields>()
        val fieldsElement = obj.get("fields")
        try {
            if (fieldsElement != null && !fieldsElement.isJsonNull) {
                val fieldsString = fieldsElement.asString

                if (!fieldsString.isNullOrBlank()) {
                    val parsedArray = JsonParser().parse(fieldsString).asJsonArray
                    parsedArray.forEach { fieldItem ->
                        val fObj = fieldItem.asJsonObject
                        val field = TaskFields(
                            label = fObj.get("label")?.asString,
                            type = fObj.get("type")?.asString,
                            value = if (fObj.get("value")?.isJsonNull == true) null else fObj.get("value")?.asString
                        )
                        fieldsList.add(field)
                    }
                } else {
                    Log.w("TaskDeserializer", "Campo 'fields' está vazio.")
                }
            }
        } catch (e: Exception) { }

        return Task(
            id = id,
            name = name,
            description = description,
            status = status,
            begin_datetime = beginDatetime,
            end_datetime = endDatetime,
            start_datetime = startDatetime,
            finish_datetime = finishDatetime,
            start_location = startLocation,
            enterprise = enterprise,
            location = location,
            fields = fieldsList
        )
    }
}
