package ten.Q7

import java.lang.StringBuilder
import kotlin.test.assertEquals

//给你一个 32 位的有符号整数 x ，返回 x 中每位上的数字反转后的结果。
//
// 如果反转后整数超过 32 位的有符号整数的范围 [−231, 231 − 1] ，就返回 0。
//假设环境不允许存储 64 位整数（有符号或无符号）。
//
//
//
// 示例 1：
//
//
//输入：x = 123
//输出：321
//
//
// 示例 2：
//
//
//输入：x = -123
//输出：-321
//
//
// 示例 3：
//
//
//输入：x = 120
//输出：21
//
//
// 示例 4：
//
//
//输入：x = 0
//输出：0
//
//
//
//
// 提示：
//
//
// -231 <= x <= 231 - 1
//
// Related Topics 数学
// 👍 2503 👎 0
fun main(args: Array<String>) {
    assertEquals(321, Solution().reverse(123))
    assertEquals(-321, Solution().reverse(-123))
    assertEquals(21, Solution().reverse(120))
    assertEquals(0, Solution().reverse(0))
    assertEquals(0, Solution().reverse(1534236469))
    assertEquals(0, Solution().reverse(-2147483648))
    assertEquals(2000022412, Solution().reverse(2142200002))
    assertEquals(-2143847412, Solution().reverse(-2147483412))
    assertEquals(214748365, Solution().reverse(563847412))
    println("success")
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    fun reverse(x: Int): Int {
        var result = 0
        var input = x
        val intMax9 = Int.MAX_VALUE / 10
        val intMin9 = Int.MIN_VALUE / 10
        var index = 0
        while (index < 9 && input != 0) {
            result = result * 10 + input % 10
            input /= 10
            index++
        }
        if (input != 0) {
            val endValue = input % 10
            if (x > 0) {
                println(" ${result} : ${intMax9} : ${endValue} : ${Int.MAX_VALUE % 10} : ${Int.MAX_VALUE}")
                if (result > intMax9 || (result == intMax9 && endValue > Int.MAX_VALUE % 10)) {
                    return 0
                }
            }
            if (x < 0) {
                if (result < intMin9 || (result == intMin9 && endValue < Int.MIN_VALUE % 10)) {
                    return 0
                }
            }
            result = result * 10 + endValue
        }
        return result
    }
}
//leetcode submit region end(Prohibit modification and deletion)