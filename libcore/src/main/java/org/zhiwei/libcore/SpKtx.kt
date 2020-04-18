package org.zhiwei.libcore

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月29日 13:31
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * SharedPreference的相关扩展，其实内部可使用腾讯开源的MMKV更高效的库
 */

object SpKtx {

    private const val DEFAULT_SP_NAME = "SpKtx"
    private lateinit var sp: SharedPreferences


    /**
     * SpKtx的初始化配置
     */
    fun init(context: Context, name: String = DEFAULT_SP_NAME): SpKtx {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return this
    }


    //region Sp相关的写入操作

    /**
     * put常规参数，目前仅支持Android原生sp的类型，也就是boolean、int、long、float、string
     */
    fun put(key: String, value: Any) {
        if (!this::sp.isInitialized) {
            throw NullPointerException("务必先调用SpKtx.init函数初始化!!!")
        }
        sp.edit(true) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                else -> throw TypeCastException("暂不支持${value.javaClass}该类型数据Sp存储")
            }
        }
    }

    /**
     * 支持存储set集合，string的元素
     */
    fun put(key: String, value: Set<String>) {
        if (!this::sp.isInitialized) {
            throw NullPointerException("务必先调用SpKtx.init函数初始化!!!")
        }
        sp.edit {
            putStringSet(key, value)
            apply()
        }
    }

    /**
     * 可以以map形式，put多个键值对
     */
    fun put(map: Map<String, Any>) {
        map.forEach { entry ->
            put(entry.key, entry.value)
        }
    }

    /**
     * 可以以pair的形式，put多个键值对
     * [pairs] 可变参数 Pair<String,Any> 当然这个any最好是Int、Long、Float、String、Boolean
     */
    fun put(vararg pairs: Pair<String, Any>) {
        pairs.forEach { pair ->
            put(pair.first, pair.second)
        }
    }

    //endregion

    //region Sp相关的读取get操作

    /**
     * 通过Key获取存储的Boolean值
     */
    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return sp.getBoolean(key, defValue)
    }

    /**
     * 通过Key获取存储的Int值
     */
    fun getInt(key: String, defValue: Int = 0): Int {
        return sp.getInt(key, defValue)
    }

    /**
     * 通过Key获取存储的Long值
     */
    fun getLong(key: String, defValue: Long = 0L): Long {
        return sp.getLong(key, defValue)
    }

    /**
     * 通过Key获取存储的Float值
     */
    fun getFloat(key: String, defValue: Float = 0f): Float {
        return sp.getFloat(key, defValue)
    }

    /**
     * 通过Key获取存储的String值
     */
    fun getString(key: String, defValue: String? = ""): String? {
        return sp.getString(key, defValue)
    }

    /**
     * 通过Key获取存储的String值
     */
    fun getStringSet(key: String, defValue: Set<String>? = emptySet()): Set<String>? {
        return sp.getStringSet(key, defValue)
    }

    /**
     * 获取存储的所有sp的值，得到的是一个map
     * @return Map<String,*>?
     */
    fun getAll(): Map<String, *>? {
        return sp.all
    }

    /**
     * 便于一些场景操作，同时获取两个key的value
     * 返回一个Pair对象
     */
    @Suppress("UNCHECKED_CAST")
    fun <A : Any, B : Any> get(one: String, two: String): Pair<A?, B?> {
        return sp.all[one] as? A to sp.all[two] as? B
    }

    /**
     * 用于一些场景，获取三个key的value
     * 返回一个Triple数据类
     */
    @Suppress("UNCHECKED_CAST")
    fun <A : Any, B : Any, C : Any> get(
        one: String,
        two: String,
        three: String
    ): Triple<A?, B?, C?> {
        return Triple(sp.all[one] as? A, sp.all[two] as? B, sp.all[three] as? C)
    }

    //endregion

    /**
     * 判断是否包含指定key
     */
    fun has(key: String): Boolean {
        return sp.contains(key)
    }

    //region sp相关的 删除操作

    /**
     * 删除指定key的存储
     * [keys]可变参数，可以多个key一起传递，操作
     */
    fun remove(vararg keys: String) {
        sp.edit {
            keys.forEach { key ->
                remove(key)
            }
            apply()
        }
    }

    /**
     * 清空当前sp
     */
    fun clear() {
        sp.edit {
            clear().apply()
        }
    }

    //endregion

}


