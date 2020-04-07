package org.zhiwei.libui

import org.junit.Test
import org.zhiwei.libcore.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月25日 14:34
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 测试扩展函数是否生效
 */
class KtxTest {

    @Test
    fun testTimeKtx() {
        println(afterTomorrowDate)
        println(beforeYesterdayDate)
        println(nowDateStr)
        println(nowDateTimeStr)
        println(nowMillis)
        println(nowTimeStr)
        println(nowWeek)
        println(tomorrowDate)
        println(tomorrowWeek)
        println(yesterdayDate)
        println(yesterdayWeek)

        println("-----------------------------------")

        println(getCnWeek(nowMillis))
        println(getDateSpan("2020-03-22"))
        println(getDateTimeSpan("2020-02-21 22:31:32"))
        println(getTimeSpan(yesterdayDate.toMillis(pattern = DATE_PATTERN)))
        println(getUsWeek(nowMillis))
        println(isAfterTomorrow(tomorrowDate.toMillis(pattern = DATE_PATTERN)))
        println(isBeforeYesterday(tomorrowDate.toMillis(pattern = DATE_PATTERN)))
        println(isToday(tomorrowDate.toMillis(pattern = DATE_PATTERN)))
        println(isTomorrow(tomorrowDate.toMillis(pattern = DATE_PATTERN)))
        println(isYesterday(tomorrowDate.toMillis(pattern = DATE_PATTERN)))
    }
}
 