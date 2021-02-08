package ten.Q9

import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

//给你一个整数 x ，如果 x 是一个回文整数，返回 ture ；否则，返回 false 。
//
// 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。例如，121 是回文，而 123 不是。
//
//
//
// 示例 1：
//
//
//输入：x = 121
//输出：true
//
//
// 示例 2：
//
//
//输入：x = -121
//输出：false
//解释：从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
//
//
// 示例 3：
//
//
//输入：x = 10
//输出：false
//解释：从右向左读, 为 01 。因此它不是一个回文数。
//
//
// 示例 4：
//
//
//输入：x = -101
//输出：false
//
//
//
//
// 提示：
//
//
// -231 <= x <= 231 - 1
//
//
//
//
// 进阶：你能不将整数转为字符串来解决这个问题吗？
// Related Topics 数学
// 👍 1378 👎 0

fun main(args: Array<String>) {
    var timeStart = System.nanoTime()
    assertEquals(false, Solution().isPalindrome(42))
    assertEquals(true, Solution().isPalindrome(121))
    assertEquals(false, Solution().isPalindrome(-121))
    assertEquals(false, Solution().isPalindrome(10))
    assertEquals(false, Solution().isPalindrome(-101))
    println("success --- ${System.nanoTime() - timeStart}---")
    //34405763
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    fun isPalindrome(x: Int): Boolean {
        if (x < 0) {
            return false
        }
        val dataList = ArrayList<Byte>()
        var input = x
        while (input > 0) {
            dataList.add((input % 10).toByte())
            input /= 10
        }
        var start = 0
        var end = dataList.size - 1
        while (start < end) {
            if (dataList[start] != dataList[end]) {
                return false
            }
            start++
            end--
        }
        return true
    }
}