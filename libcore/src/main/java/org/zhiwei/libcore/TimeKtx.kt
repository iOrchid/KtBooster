package org.zhiwei.libcore

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月25日 13:46
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 关于日期、时间 的工具类，不使用扩展泛型的方式了，那样会在使用方看到太多太多的扩展函数、属性，而凌乱
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 */

object TimeKt {

    //标准时间string的格式pattern
    const val FULL_PATTERN = "yyyy-MM-dd EE HH:mm:ss"
    const val MILLIS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSXXX"
    const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    const val TIME_PATTERN = "HH:mm:ss"
    const val DATE_PATTERN = "yyyy-MM-dd"
    const val WEEK_PATTERN = "EE"
    private const val ONE_DAY_MILLIS = 24 * 60 * 60 * 1000L//一天的毫秒值


//region 时间相关的扩展属性字段

    /**
     * 当前时间毫秒值
     */
    val nowMillis: Long
        get() = System.currentTimeMillis()

    /**
     * 今天的星期，默认格式EE
     */
    val nowWeek: String
        get() {
            /*
             * 根据选择的Locale不同，显示对应语言的 默认是medium
             * getDateInstance
             * DateFormat.FULL 2020年3月25日 星期三
             * DateFormat.LONG 2020年3月25日
             * DateFormat.MEDIUM 2020-3-25
             * DateFormat.SHORT 20-3-25
             * getTimeInstance
             * DateFormat.FULL 下午02时49分49秒 CST
             * DateFormat.LONG 下午02时50分11秒
             * DateFormat.MEDIUM 14:49:11
             * DateFormat.SHORT 下午2:50
             * getDateTimeInstance 是两种的组合
             */
            return SimpleDateFormat(WEEK_PATTERN, Locale.getDefault()).format(nowMillis)
        }

    /**
     * 昨天的星期，默认格式EE
     */
    val yesterdayWeek: String
        get() = SimpleDateFormat(
            WEEK_PATTERN,
            Locale.getDefault()
        ).format(nowMillis - ONE_DAY_MILLIS)

    /**
     * 明天的星期，默认格式EE
     */
    val tomorrowWeek: String
        get() = SimpleDateFormat(
            WEEK_PATTERN,
            Locale.getDefault()
        ).format(nowMillis + ONE_DAY_MILLIS)


    /**
     * 当前时间 标准格式的string，后续可以扩展更多time相关的属性和函数
     */
    val nowDateTimeStr: String
        get() = SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault()).format(nowMillis)

    /**
     * 当前时间 标准格式的string，后续可以扩展更多time相关的属性和函数
     */
    val nowTimeStr: String
        get() = SimpleDateFormat(TIME_PATTERN, Locale.getDefault()).format(nowMillis)

    /**
     * 当前时间 标准格式的string，后续可以扩展更多time相关的属性和函数
     */
    val nowDateStr: String
        get() = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(nowMillis)

    /**
     * 明天 标准格式的 日期样式 yyyy-MM-dd
     */
    val tomorrowDate: String
        get() = SimpleDateFormat(
            DATE_PATTERN,
            Locale.getDefault()
        ).format(nowMillis + ONE_DAY_MILLIS)

    /**
     * 后天 标准格式的 日期样式 yyyy-MM-dd
     */
    val afterTomorrowDate: String
        get() = SimpleDateFormat(
            DATE_PATTERN,
            Locale.getDefault()
        ).format(nowMillis + 2 * ONE_DAY_MILLIS)

    /**
     * 后天 标准格式的 日期样式 yyyy-MM-dd
     */
    val yesterdayDate: String
        get() = SimpleDateFormat(
            DATE_PATTERN,
            Locale.getDefault()
        ).format(nowMillis - ONE_DAY_MILLIS)

    /**
     * 后天 标准格式的 日期样式 yyyy-MM-dd
     */
    val beforeYesterdayDate: String
        get() = SimpleDateFormat(
            DATE_PATTERN,
            Locale.getDefault()
        ).format(nowMillis - 2 * ONE_DAY_MILLIS)


//endregion


//region 扩展的函数

    /**
     * 判断是否是前天
     * [millis] 时间戳 毫秒值
     */
    fun isBeforeYesterday(millis: Long): Boolean {
        return nowDateStr == (millis + 2 * ONE_DAY_MILLIS).toDate()
    }

    /**
     * 判断是否是昨天
     * [millis] 时间戳 毫秒值
     */
    fun isYesterday(millis: Long): Boolean {
        return nowDateStr == (millis + ONE_DAY_MILLIS).toDate()
    }

    /**
     * 判断是否是今天
     * [millis] 时间戳 毫秒值
     */
    fun isToday(millis: Long): Boolean {
        return nowDateStr == millis.toDate()
    }

    /**
     * 判断是否是明天
     * [millis] 时间戳 毫秒值
     */
    fun isTomorrow(millis: Long): Boolean {
        return nowDateStr == (millis - ONE_DAY_MILLIS).toDate()
    }

    /**
     * 判断是否是后天
     * [millis] 时间戳 毫秒值
     */
    fun isAfterTomorrow(millis: Long): Boolean {
        return nowDateStr == (millis - 2 * ONE_DAY_MILLIS).toDate()
    }

    /**
     * 指定毫秒值，转化为英文的星期
     * [millis] 时间戳 毫秒值
     */
    fun getUsWeek(millis: Long): String {
        return SimpleDateFormat(WEEK_PATTERN, Locale.US).format(millis)
    }

    /**
     * 指定毫秒值，转化为中文星期
     * [millis] 时间戳 毫秒值
     */
    fun getCnWeek(millis: Long): String {
        return SimpleDateFormat(WEEK_PATTERN, Locale.CHINA).format(millis)
    }

    /**
     * 指定毫秒值，转化为标准格式的日期
     * [millis] 时间戳 毫秒值
     * [pattern] 格式化的方式 默认 yyyy-MM-dd HH:mm:ss
     */
    fun toDateTimeStr(millis: Long, pattern: String = DATE_TIME_PATTERN): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(millis)
    }

    /**
     * 时间string转为毫秒millis
     */
    @Throws(ParseException::class)
    fun String.toMillis(pattern: String = DATE_TIME_PATTERN): Long {
        return requireNotNull(SimpleDateFormat(pattern, Locale.getDefault()).parse(this)?.time)
    }

    /**
     * 对比两个时间的差 天数
     * [source] 第一个时间毫秒值
     * [destination] 第二个时间毫秒值
     * @return 返回的天数
     */
    fun getTimeSpan(source: Long, destination: Long = nowMillis): Int {
        return ((source - destination) / ONE_DAY_MILLIS).toInt()
    }

    /**
     * 日期格式 yyyy-MM-dd的时间 string对比间隔
     * [source] 第一个时间
     * [destination] 第二个时间
     */
    @Throws(ParseException::class)
    fun getDateSpan(
        source: String,
        destination: String = nowDateStr,
        pattern: String = DATE_PATTERN
    ): Int {
        val sourceMillis =
            requireNotNull(SimpleDateFormat(pattern, Locale.getDefault()).parse(source)?.time)
        val destinationMillis =
            requireNotNull(SimpleDateFormat(pattern, Locale.getDefault()).parse(destination)?.time)
        return ((sourceMillis - destinationMillis) / ONE_DAY_MILLIS).toInt()
    }

    /**
     * 日期格式 yyyy-MM-dd HH:mm:ss  的时间 string对比间隔
     * [source] 第一个时间
     * [destination] 第二个时间
     * 返回相差天数
     */
    fun getDateTimeSpan(
        source: String,
        destination: String = nowDateTimeStr,
        pattern: String = DATE_TIME_PATTERN
    ): Int {
        val sourceMillis =
            requireNotNull(SimpleDateFormat(pattern, Locale.getDefault()).parse(source)?.time)
        val destinationMillis =
            requireNotNull(SimpleDateFormat(pattern, Locale.getDefault()).parse(destination)?.time)
        return ((sourceMillis - destinationMillis) / ONE_DAY_MILLIS).toInt()
    }


    /**
     * 内部使用 毫秒值，转为 yyyy-MM-dd 格式的string日期，用于比较
     */
    private fun Long.toDate(): String =
        SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(this)

//endregion


}