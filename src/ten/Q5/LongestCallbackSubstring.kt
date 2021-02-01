package ten.Q5

import kotlin.test.assertEquals

//给你一个字符串 s，找到 s 中最长的回文子串。
//
//
//
// 示例 1：
//
//
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
//
//
// 示例 2：
//
//
//输入：s = "cbbd"
//输出："bb"
//
//
// 示例 3：
//
//
//输入：s = "a"
//输出："a"
//
//
// 示例 4：
//
//
//输入：s = "ac"
//输出："a"
//
//
//
//
// 提示：
//
//
// 1 <= s.length <= 1000
// s 仅由数字和英文字母（大写和/或小写）组成
//
// Related Topics 字符串 动态规划
// 👍 3143 👎 0
fun main(args: Array<String>) {
    assertEquals(Solution().longestPalindrome("babad"),"bab")
    assertEquals(Solution().longestPalindrome("cbbd"),"bb")
    assertEquals(Solution().longestPalindrome("a"),"a")
    assertEquals(Solution().longestPalindrome("ac"),"a")
    assertEquals(Solution().longestPalindrome("abcdeeeedcba"),"abcdeeeedcba")
    assertEquals(Solution().longestPalindrome("2abcdeeeedcdba4"),"cdeeeedc")
    assertEquals(Solution().longestPalindrome("abcdeeedcba"),"abcdeeedcba")
    assertEquals(Solution().longestPalindrome("dabcdeeedcbas"),"abcdeeedcba")
    println("success")

}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    var maxIndex: Int = 0
    var maxLength: Int = 1

    fun longestPalindrome(s: String): String {
        var index = 0
        for (char: Char in s) {
            if ((s.lastIndex - index) * 2 + 1 <= maxLength) {
                break
            }
            var leftIndex: Int = index - 1
            var rightIndex: Int = index + 1
            pick(s, leftIndex, rightIndex)
            if (rightIndex < s.length && s[rightIndex].equals(char)) {
                rightIndex++
                pick(s, leftIndex, rightIndex)
            }
            index++
        }
        return s.substring(maxIndex, maxIndex + maxLength)
    }

    fun pick(s: String, left: Int, right: Int) {
        var leftIndex = left
        var rightIndex = right
        while (true) {
            if (leftIndex < 0 || rightIndex >= s.length) {
                break
            }
            if (s[leftIndex] != s[rightIndex]) {
                break
            }
            leftIndex--
            rightIndex++
        }
        leftIndex++
        rightIndex--
        var length = rightIndex - leftIndex + 1
        if (length > maxLength) {
            maxIndex = leftIndex
            maxLength = length
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
