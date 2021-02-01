package ten.Q3

import kotlin.test.assertEquals

//给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
//
//
//
// 示例 1:
//
//
//输入: s = "abcabcbb"
//输出: 3
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
//
//
// 示例 2:
//
//
//输入: s = "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
//
//
// 示例 3:
//
//
//输入: s = "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
//
//
// 示例 4:
//
//
//输入: s = ""
//输出: 0
//
//
//
//
// 提示：
//
//
// 0 <= s.length <= 5 * 104
// s 由英文字母、数字、符号和空格组成
//
// Related Topics 哈希表 双指针 字符串 Sliding Window
// 👍 4861 👎 0
fun main(args: Array<String>) {
    assertEquals(Solution().lengthOfLongestSubstring("abcabcbb"), 3)
    println("success")
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        var pickElement = HashSet<Char>()
        //max区间前闭后闭
        var maxLength = 0
        var indexStart = 0
        var indexEnd = 0
        for (element: Char in s) {
            if (pickElement.contains(element)) {
                var oldIndexStart = indexStart
                if (indexEnd - indexStart > maxLength) {
                    maxLength = indexEnd - indexStart
                }
                indexStart = s.indexOf(element, oldIndexStart) + 1
                pickElement.removeAll(s.substring(oldIndexStart, indexStart - 1).toList())
            }
            indexEnd++
            pickElement.add(element)
        }
        if (indexEnd - indexStart > maxLength) {
            maxLength = indexEnd - indexStart
        }
        return maxLength
    }
}
//leetcode submit region end(Prohibit modification and deletion)