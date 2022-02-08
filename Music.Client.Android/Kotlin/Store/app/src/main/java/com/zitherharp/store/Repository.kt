package com.zitherharp.store

import com.zitherharp.store.model.Item
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

object Repository {
    val itemMap: MutableMap<String, Item> = HashMap()

    init {
        var jsonValue: JSONArray
        // TODO: Load items
        var jsonValues = getJsonValues("item")
        for (i in 0 until jsonValues.length()) {
            jsonValue = jsonValues.getJSONArray(i)
            itemMap[jsonValue.requireString(0)] = Item(
                jsonValue.requireString(0),
                jsonValue.requireString(1),
                jsonValue.requireString(2),
                jsonValue.requireString(3),
                jsonValue.requireString(4))
        }
        // TODO: Load field
        jsonValues = getJsonValues("field")
        for (i in 0 until jsonValues.length()) {
            jsonValue = jsonValues.getJSONArray(i)
            itemMap[jsonValue.requireString(0)]?.let {
                it.field = Item.Field(
                    jsonValue.requireString(1),
                    jsonValue.requireDouble(2),
                    jsonValue.requireString(3),
                    jsonValue.requireInt(4),
                    jsonValue.requireString(5),
                    jsonValue.requireString(6))
            }
        }
        // TODO: Load image
        jsonValues = getJsonValues("image")
        for (i in 0 until jsonValues.length()) {
            jsonValue = jsonValues.getJSONArray(i)
            itemMap[jsonValue.requireString(0)]?.let {
                it.image = Item.Image(
                    jsonValue.requireString(1),
                    jsonValue.requireString(2),
                    jsonValue.requireString(3))
            }
        }
        // TODO: Load contact
        jsonValues = getJsonValues("contact")
        for (i in 0 until jsonValues.length()) {
            jsonValue = jsonValues.getJSONArray(i)
            itemMap[jsonValue.requireString(0)]?.let {
                it.contact = Item.Contact(
                    jsonValue.requireString(1),
                    jsonValue.requireString(2),
                    jsonValue.requireString(3),
                    jsonValue.requireString(4))
            }
        }
    }

    private fun getJsonValues(name: String): JSONArray {
        val url = "https://sheets.googleapis.com/v4/spreadsheets/" +
                "1jjImV0A_U9ecTzPfdoxJmc3TxiMr3YMb3qEeAcCmu1g/values/" +
                "$name?key=AIzaSyAD91OiEeWRoqhsw0peq94qg5joZe47r_s"
        val jsonString = URL(url).readText()
        val jsonObject = JSONObject(jsonString)
        return jsonObject.getJSONArray("values")
    }

    private fun JSONArray.requireString(index: Int) = try { getString(index) } catch (e: Exception) { "" }
    private fun JSONArray.requireInt(index: Int) = try { getInt(index) } catch (e: Exception) { 0 }
    private fun JSONArray.requireDouble(index: Int) = try { getDouble(index) } catch (e: Exception) { 0.0 }
}