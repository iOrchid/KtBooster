package org.zhiwei.libcore

import org.junit.Test
import org.zhiwei.libcore.TimeKt.DATE_PATTERN
import org.zhiwei.libcore.TimeKt.toMillis

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月16日 23:37
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * CoreKtx的扩展函数 单元测试类
 */
class CoreKtxTest {

    @Test
    fun testTimeKtx() {

        val threadName = Thread.currentThread().name
        println(threadName)

        val trace = Thread.currentThread().stackTrace.forEach { element ->
            println("className: ${element.className} fileName:${element.fileName} methodName ${element.methodName} lineNO ${element.lineNumber}")

        }

        println("-----------------------------------")

        println(TimeKt.afterTomorrowDate)
        println(TimeKt.beforeYesterdayDate)
        println(TimeKt.nowDateStr)
        println(TimeKt.nowDateTimeStr)
        println(TimeKt.nowMillis)
        println(TimeKt.nowTimeStr)
        println(TimeKt.nowWeek)
        println(TimeKt.tomorrowDate)
        println(TimeKt.tomorrowWeek)
        println(TimeKt.yesterdayDate)
        println(TimeKt.yesterdayWeek)

        println("-----------------------------------")

        println(TimeKt.getCnWeek(TimeKt.nowMillis))
        println(TimeKt.getDateSpan("2020-03-22"))
        println(TimeKt.getDateTimeSpan("2020-02-21 22:31:32"))
        println(TimeKt.getTimeSpan(TimeKt.yesterdayDate.toMillis(pattern = DATE_PATTERN)))
        println(TimeKt.getUsWeek(TimeKt.nowMillis))
        println(TimeKt.isAfterTomorrow(TimeKt.tomorrowDate.toMillis(pattern = DATE_PATTERN)))
        println(TimeKt.isBeforeYesterday(TimeKt.tomorrowDate.toMillis(pattern = DATE_PATTERN)))
        println(TimeKt.isToday(TimeKt.tomorrowDate.toMillis(pattern = DATE_PATTERN)))
        println(TimeKt.isTomorrow(TimeKt.tomorrowDate.toMillis(pattern = DATE_PATTERN)))
        println(TimeKt.isYesterday(TimeKt.tomorrowDate.toMillis(pattern = DATE_PATTERN)))
    }
}