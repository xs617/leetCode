package ten.Q10

import kotlin.test.assertEquals

//给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
//
//
// '.' 匹配任意单个字符
// '*' 匹配零个或多个前面的那一个元素
//
//
// 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
//
//
// 示例 1：
//
//
//输入：s = "aa" p = "a"
//输出：false
//解释："a" 无法匹配 "aa" 整个字符串。
//
//
// 示例 2:
//
//
//输入：s = "aa" p = "a*"
//输出：true
//解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
//
//
// 示例 3：
//
//
//输入：s = "ab" p = ".*"
//输出：true
//解释：".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
//
//
// 示例 4：
//
//
//输入：s = "aab" p = "c*a*b"
//输出：true
//解释：因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。
//
//
// 示例 5：
//
//
//输入：s = "mississippi" p = "mis*is*p*."
//输出：false
//
//
//
// 提示：
//
//
// 0 <= s.length <= 20
// 0 <= p.length <= 30
// s 可能为空，且只包含从 a-z 的小写字母。
// p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
// 保证每次出现字符 * 时，前面都匹配到有效的字符
//
// Related Topics 字符串 动态规划 回溯算法
// 👍 1833 👎 0
fun main(args: Array<String>) {
    var timeStart = System.nanoTime()
    assertEquals(true, Solution().isMatch("abcaaaaaaabaabcabac", ".*ab.a.*a*a*.*b*b*"))
    assertEquals(true, Solution().isMatch("a", "aa*.*b*c*d*f*.*"))
    assertEquals(true, Solution().isMatch("abbabaaaaaaacaa", "a*.*b.a.*c*b*a*c*"))
    assertEquals(true, Solution().isMatch("ba", ".*."))
    assertEquals(true, Solution().isMatch("abaaaabaab", "a*.*ba.*c*.."))
    assertEquals(true, Solution().isMatch("ab", ".*c*.."))
    assertEquals(true, Solution().isMatch("abbaaaabaabbcba", "a*.*ba.*c*..a*.a*."))
    assertEquals(false, Solution().isMatch("", "..*"))
    assertEquals(false, Solution().isMatch("b", "aaa."))
    assertEquals(false, Solution().isMatch("b", "a"))
    assertEquals(true, Solution().isMatch("ab", ".*.."))


    assertEquals(false, Solution().isMatch("aa", "a"))
    assertEquals(true, Solution().isMatch("aa", "a*"))
    assertEquals(true, Solution().isMatch("ab", ".*"))
    assertEquals(true, Solution().isMatch("aab", "c*a*b"))
    assertEquals(false, Solution().isMatch("mississippi", "mis*is*p*."))

    //.匹配串为空
    assertEquals(false, Solution().isMatch("", "."))
    //都为空
    assertEquals(true, Solution().isMatch("", ""))
    //regex为空
    assertEquals(false, Solution().isMatch("a", ""))

    assertEquals(true, Solution().isMatch("abc", "abc"))

    assertEquals(true, Solution().isMatch("a", "."))
    assertEquals(false, Solution().isMatch("aa", "."))
    assertEquals(false, Solution().isMatch("aa", "a"))

    assertEquals(true, Solution().isMatch("abc", "ab*c"))
    assertEquals(true, Solution().isMatch("abbc", "ab*c"))
    assertEquals(true, Solution().isMatch("ac", "ab*c"))
    assertEquals(true, Solution().isMatch("", "a*b*c*"))

    //.* 匹配串为空
    assertEquals(true, Solution().isMatch("", ".*"))
    //.* 匹配串为空
    assertEquals(true, Solution().isMatch("asdfafdasdfs", ".*"))
    //.*在中间且回溯
    assertEquals(true, Solution().isMatch("abdejjeebb", "ab.*ebb"))
    assertEquals(false, Solution().isMatch("abdejjeejbb", "ab.*ebb"))
    //.*在中间未回溯
    assertEquals(true, Solution().isMatch("abebb", "ab.*ebb"))
    //.*在头且回溯
    assertEquals(true, Solution().isMatch("ebebeebb", ".*ebb"))
    assertEquals(true, Solution().isMatch("aaa", "a*a"))
    //.*在头未回溯
    assertEquals(true, Solution().isMatch("ebb", ".*ebb"))
    //.*在尾部
    assertEquals(true, Solution().isMatch("ebb", "ebb.*"))

    println("success --- ${System.nanoTime() - timeStart}---")
//    20887312
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    lateinit var input: String
    lateinit var regex: String
    val cacheMap = HashMap<String, Boolean>()

    fun isMatch(s: String, p: String): Boolean {
//        println(" ----- start  $s : $p------")
        input = s
        regex = p
        return matchRegex(0, 0)
    }

    fun matchRegex(inputIndex: Int, regexIndex: Int): Boolean {
        val currentKey = "${inputIndex}_$regexIndex"
        val currentMatch = cacheMap.get(currentKey)
        if (currentMatch != null) {
//            println(" ++++++++ hint cache ++++++++")
            return currentMatch
        }
        if (regexIndex >= regex.length) {
            return inputIndex >= input.length
        }
        if (regexIndex + 1 >= regex.length || regex[regexIndex + 1] != '*') {
            val result = matchChar(inputIndex, regexIndex) && matchRegex(inputIndex + 1, regexIndex + 1)
            cacheMap.put(currentKey, result)
            return result
        } else {
            val nextCharMatch = matchChar(inputIndex, regexIndex) && matchRegex(inputIndex + 1, regexIndex)
            val nextRegexMatch = matchRegex(inputIndex, regexIndex + 2)
            val result = nextCharMatch || nextRegexMatch
            cacheMap.put(currentKey, result)
            return result
        }
    }

    fun matchChar(inputIndex: Int, regexIndex: Int): Boolean {
        return inputIndex < input.length && regexIndex < regex.length && (input[inputIndex] == regex[regexIndex] || regex[regexIndex] == '.')
    }
}