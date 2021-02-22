package twenty.Q12

import java.lang.StringBuilder
import kotlin.test.assertEquals

//罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
//
//
//字符          数值
//I             1
//V             5
//X             10
//L             50
//C             100
//D             500
//M             1000
//
// 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做 XXVII, 即为 XX + V + I
//I 。
//
// 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5
// 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
//
//
// I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
// X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
// C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
//
//
// 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
//
// 示例 1:
//
//
//输入: 3
//输出: "III"
//
// 示例 2:
//
//
//输入: 4
//输出: "IV"
//
// 示例 3:
//
//
//输入: 9
//输出: "IX"
//
// 示例 4:
//
//
//输入: 58
//输出: "LVIII"
//解释: L = 50, V = 5, III = 3.
//
//
// 示例 5:
//
//
//输入: 1994
//输出: "MCMXCIV"
//解释: M = 1000, CM = 900, XC = 90, IV = 4.
//
//
//
// 提示：
//
//
// 1 <= num <= 3999
//
// Related Topics 数学 字符串
// 👍 489 👎 0
fun main(args: Array<String>) {
    var timeStart = System.nanoTime()

    assertEquals("I", Solution().intToRoman(1))
    assertEquals("III", Solution().intToRoman(3))
    assertEquals("VIII", Solution().intToRoman(8))
    assertEquals("IV", Solution().intToRoman(4))
    assertEquals("IX", Solution().intToRoman(9))
    assertEquals("X", Solution().intToRoman(10))
    assertEquals("LVIII", Solution().intToRoman(58))
    assertEquals("DCLXVI", Solution().intToRoman(666))
    assertEquals("MX", Solution().intToRoman(1010))
    assertEquals("MCMXCIV", Solution().intToRoman(1994))
    assertEquals("MMCCCXXXIII", Solution().intToRoman(2333))
    assertEquals("MMDLXVIII", Solution().intToRoman(2568))
    assertEquals("MMMCDXCIV", Solution().intToRoman(3494))
    assertEquals("MMMCMXCIX", Solution().intToRoman(3999))
    println("success --- ${System.nanoTime() - timeStart}---")
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    val base = listOf<Int>(1000, 100, 10, 1)
    val baseChar = listOf<Char>('M', 'C', 'X', 'I')
    val halfChar = listOf<Char>('D', 'L', 'V')
    fun intToRoman(num: Int): String {
        var baseIndex = 0
        val result = StringBuilder()
        var convertNum = num
        while (baseIndex < base.size) {
            var baseNumber = convertNum / base[baseIndex]
            var baseCount = baseNumber
            if (baseNumber == 9) {
                result.append(baseChar[baseIndex])
                result.append(baseChar[baseIndex - 1])
                baseCount = 0
            } else if (baseNumber == 4) {
                result.append(baseChar[baseIndex])
                result.append(halfChar[baseIndex - 1])
                baseCount = 0
            } else if (baseNumber >= 5) {
                result.append(halfChar[baseIndex - 1])
                baseCount -= 5
            }
            while (baseCount > 0) {
                result.append(baseChar[baseIndex])
                baseCount--
            }
            convertNum %= base[baseIndex]
            baseIndex++
        }
        return result.toString()
    }
}
//leetcode submit region end(Prohibit modification and deletion)