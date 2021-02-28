package org.zhiwei.libcore.kv

/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 抽象接口，本地存储key-value的工具类
 */
interface KvInterface {

    fun put(key: String, value: Any?)

    fun getBoolean(key: String, defValue: Boolean = false): Boolean

    fun getByteArray(key: String, defValue: ByteArray? = null): ByteArray?

    fun getFloat(key: String, defValue: Float = 0f): Float

    fun getInt(key: String, defValue: Int = 0): Int

    fun getLong(key: String, defValue: Long = 0L): Long

    fun getString(key: String, defValue: String? = null): String?

    fun removeValue(key: String)

    fun removeValues(keys: Array<String>)

    fun clearAll()

}