package com.millet.mylibrary.util.mmkv

import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author  zyl
 * @date  2020/7/16 10:10 AM
 */
class MmkvUtil<T>(val key: String, private val default: T) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return decode(key, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        encode(key, value)
    }

    private fun <U> decode(key: String, default: U): U = with(MMKV.defaultMMKV()) {
        val res = when (default) {
            is Int -> decodeInt(key, default)
            is Long -> decodeLong(key, default)
            is Float -> decodeFloat(key, default)
            is Double -> decodeDouble(key, default)
            is Boolean -> decodeBool(key, default)
            is String -> decodeString(key, default)
            is ByteArray -> decodeBytes(key, default)
            else -> throw IllegalArgumentException("This type can not be exist mmkv")
        }
        return@with res as U
    }

    private fun <U> encode(key: String, value: U) = with(MMKV.defaultMMKV()) {
        when (value) {
            is Int -> encode(key, value)
            is Long -> encode(key, value)
            is Float -> encode(key, value)
            is Double -> encode(key, value)
            is Boolean -> encode(key, value)
            is String -> encode(key, value)
            is ByteArray -> encode(key, value)
            else -> throw IllegalArgumentException("This type can not be saved into mmkv")
        }
    }

}