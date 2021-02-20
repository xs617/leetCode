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
//    27415355
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    lateinit var input: String
    lateinit var regex: String
    val star: Byte = 42
    val point: Byte = 46
    val any: Byte = 0
    val end: Byte = -1
    val regexInfoList = ArrayList<RegexInfo>()

    fun isMatch(s: String, p: String): Boolean {
//        println(" ------------     start : $s : $p     -----------")
        input = s
        regex = p
        if (s.isEmpty() && p.isEmpty()) {
            return true
        }
        exploreRegexInfo()
//        regexInfoList.forEach { println(" +++++++++++++  ${it.matchChar} : ${it.matchMode} : ${it.matchEndChar} ++++++++++") }
        return parseRegex(inputStartIndex = 0, regStartIndex = 0)
    }

    fun parseRegex(inputStartIndex: Int, regStartIndex: Int): Boolean {
//        println(" ------------     parseRegex  : $inputStartIndex : $regStartIndex     -----------")

        var regCurIndex = regStartIndex
        var inputCurIndex = inputStartIndex
        out@ while (regCurIndex < regexInfoList.size) {
            val curRegexInfo = regexInfoList[regCurIndex]
            regCurIndex++
            when (curRegexInfo.matchMode) {
                MatchMode.Single -> {
                    //可匹配任何值或者与待匹配值一致，则匹配下一个，否则匹配失败
                    if (inputCurIndex < input.length && (curRegexInfo.matchChar == any || curRegexInfo.matchChar == input[inputCurIndex].toByte())) {
                        inputCurIndex++
                    } else {
                        return false
                    }
                }
                MatchMode.MatchUntil -> {
                    var deepInputIndex = inputCurIndex
                    while (deepInputIndex < input.length) {
                        val deepInputChar = input[deepInputIndex].toByte()
                        //文字匹配
                        if (curRegexInfo.matchChar == deepInputChar || curRegexInfo.matchChar == any) {
                            if (curRegexInfo.matchEndChar.contains(any) || curRegexInfo.matchEndChar.contains(deepInputChar)) {
                                //结束符匹配这个字符本身
                                //进行尝试
                                if (parseRegex(deepInputIndex, regCurIndex)) {
                                    return true
                                }
                            }
                            deepInputIndex++
                        } else {
                            inputCurIndex = deepInputIndex
                            continue@out
                        }
                    }
                    if (regCurIndex >= regexInfoList.size || curRegexInfo.matchEndChar.contains(end)) {
                        return true
                    }
                }
            }
        }
        return inputCurIndex >= input.length
    }

    fun exploreRegexInfo() {
        var regexIndex = 0
        while (regexIndex < regex.length) {
            val curRegex = regex[regexIndex].toByte()
            if (curRegex == star) {
                regexInfoList.last().matchMode = MatchMode.MatchUntil
            } else if (curRegex == point) {
                regexInfoList.add(RegexInfo(MatchMode.Single, any, HashSet()))
            } else {
                regexInfoList.add(RegexInfo(MatchMode.Single, curRegex, HashSet()))
            }
            regexIndex++
        }
        recallToSetEnd()
    }

    fun recallToSetEnd() {
        var index = regexInfoList.size - 2
        var lastRecallRegexInfo: RegexInfo = regexInfoList.lastOrNull() ?: return
        lastRecallRegexInfo.matchEndChar.add(end)
        var recallRegexInfo: RegexInfo? = null
        while (index >= 0) {
            recallRegexInfo = regexInfoList[index]
            when (lastRecallRegexInfo.matchMode) {
                MatchMode.Single -> {
                    recallRegexInfo.matchEndChar.add(lastRecallRegexInfo.matchChar)
                }
                MatchMode.MatchUntil -> {
                    recallRegexInfo.matchEndChar.add(lastRecallRegexInfo.matchChar)
                    recallRegexInfo.matchEndChar.addAll(lastRecallRegexInfo.matchEndChar)
                }
            }
            lastRecallRegexInfo = recallRegexInfo
            index--
        }
    }

    class RegexInfo(var matchMode: MatchMode, var matchChar: Byte, var matchEndChar: HashSet<Byte>) {

    }

    enum class MatchMode {
        Single,
        MatchUntil
    }
}