package org.zhiwei.booster.core.kv

import android.content.Context
import com.tencent.mmkv.MMKV

/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 */
object KvTools : KvInterface {

    private lateinit var kv: KvInterface

    /**
     * 初始化KvTools
     */
    fun initKvTools(context: Context): KvInterface {
        return if (this::kv.isInitialized) kv else DefaultKv.initialize(context).also { kv = it }
    }

    override fun put(key: String, value: Any?) {
        check(this::kv.isInitialized)
        DefaultKv.put(key, value)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        check(this::kv.isInitialized)
        return kv.getBoolean(key, defValue)
    }

    override fun getByteArray(key: String, defValue: ByteArray?): ByteArray? {
        check(this::kv.isInitialized)
        return kv.getByteArray(key, defValue)
    }

    override fun getFloat(key: String, defValue: Float): Float {
        check(this::kv.isInitialized)
        return kv.getFloat(key, defValue)
    }

    override fun getInt(key: String, defValue: Int): Int {
        check(this::kv.isInitialized)
        return kv.getInt(key, defValue)
    }

    override fun getLong(key: String, defValue: Long): Long {
        check(this::kv.isInitialized)
        return kv.getLong(key, defValue)
    }

    override fun getString(key: String, defValue: String?): String? {
        check(this::kv.isInitialized)
        return kv.getString(key, defValue)
    }

    override fun removeValue(key: String) {
        check(this::kv.isInitialized)
        return kv.removeValue(key)
    }

    override fun removeValues(keys: Array<String>) {
        check(this::kv.isInitialized)
        return kv.removeValues(keys)
    }

    override fun clearAll() {
        check(this::kv.isInitialized)
        return kv.clearAll()
    }

}

/**
 * 默认实现sp功能的key-value工具类
 */
private object DefaultKv : KvInterface {

    //私有kv对象
    private val kv: MMKV by lazy {
        MMKV.defaultMMKV()
    }

    /**
     * 初始化MMKV的配置
     */
    fun initialize(context: Context): DefaultKv {
        MMKV.initialize(context)
        return this
    }

    override fun put(key: String, value: Any?) {
        when (value) {
            is Boolean -> kv.putBoolean(key, value)
            is ByteArray -> kv.putBytes(key, value)
            is Float -> kv.putFloat(key, value)
            is Int -> kv.putInt(key, value)
            is Long -> kv.putLong(key, value)
            is String -> kv.putString(key, value)
            else -> error("${value?.javaClass?.simpleName} KvInterface暂不支持的类型")
        }
    }

    override fun getBoolean(key: String, defValue: Boolean) = kv.getBoolean(key, defValue)

    override fun getByteArray(key: String, defValue: ByteArray?) =
        kv.getBytes(key, defValue) ?: byteArrayOf()

    override fun getFloat(key: String, defValue: Float) = kv.getFloat(key, defValue)

    override fun getInt(key: String, defValue: Int) = kv.getInt(key, defValue)

    override fun getLong(key: String, defValue: Long) = kv.getLong(key, defValue)

    override fun getString(key: String, defValue: String?) = kv.getString(key, defValue)

    override fun removeValue(key: String) = kv.removeValueForKey(key)

    override fun removeValues(keys: Array<String>) = kv.removeValuesForKeys(keys)

    override fun clearAll() = kv.clearAll()

}