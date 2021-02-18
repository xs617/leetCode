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
//    30344809
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    lateinit var input: String
    lateinit var regex: String
    val star: Byte = 42
    val point: Byte = 46
    val any: Byte = 0
    val invalid: Byte = -1
    fun isMatch(s: String, p: String): Boolean {
        println(" ------------     start : $s : $p     -----------")
        input = s
        regex = p
        if (s.isEmpty() && p.isEmpty()) {
            return true
        }
        return parseRegex(inputStartIndex = 0, regStartIndex = 0)
    }

    fun parseRegex(inputStartIndex: Int, regStartIndex: Int): Boolean {
        println(" ------------     parseRegex  : $inputStartIndex : $regStartIndex     -----------")

        var regCurIndex = regStartIndex
        var inputCurIndex = inputStartIndex
        while (regCurIndex < regex.length) {
            var curChar = regex[regCurIndex].toByte()
            if (curChar == star) {
                //星星前置解析过了，不需要再操作
                continue
            } else {
                val regexFormat = exploreRegexInfo(regCurIndex, MatchMode.None)
                println(" $regCurIndex :  ${regexFormat?.matchMode}: ${regexFormat?.matchChar} :${regexFormat?.matchEndChar}")
                //匹配
                when (regexFormat?.matchMode) {
                    MatchMode.None -> {
                        //模式异常
                        return false
                    }
                    MatchMode.SpecificChar -> {
                        regCurIndex++
                        if (inputCurIndex < input.length && input[inputCurIndex].toByte() == regexFormat.matchChar) {
                            inputCurIndex++
                        } else {
                            return false
                        }
                    }
                    MatchMode.AnyChar -> {
                        regCurIndex++
                        if (input.length > inputCurIndex) {
                            inputCurIndex++
                        } else {
                            return false
                        }
                    }
                    MatchMode.SpecificCharUntil,
                    MatchMode.AnyCharUntil -> {
                        regCurIndex += 2
                        var deepInputIndex = inputCurIndex
                        if (regexFormat.matchMode == MatchMode.SpecificCharUntil) {
                            while (deepInputIndex < input.length) {
                                val deepInputChar = input[deepInputIndex].toByte()
                                if (regexFormat.matchChar == deepInputChar) {
                                    if (regexFormat.matchEndChar.contains(any) || regexFormat.matchEndChar.contains(deepInputChar)) {
                                        //结束符匹配这个字符本身
                                        //进行尝试
                                        if (parseRegex(deepInputIndex, regCurIndex)) {
                                            return true
                                        }
                                    }
                                    deepInputIndex++
                                } else {
                                    break
                                }
                            }
                            if (parseRegex(deepInputIndex, regCurIndex)){
                                return true
                            }
                        } else if (regexFormat.matchMode == MatchMode.AnyCharUntil) {
                            while (deepInputIndex < input.length) {
                                val deepInputChar = input[deepInputIndex].toByte()
                                if (regexFormat.matchEndChar.contains(any) || regexFormat.matchEndChar.contains(deepInputChar)) {
                                    if (parseRegex(deepInputIndex, regCurIndex)) {
                                        return true
                                    }
                                }
                                deepInputIndex++
                            }
                            if (parseRegex(deepInputIndex, regCurIndex)){
                                return true
                            }
                        }
                    }
                }
            }
        }
        return inputCurIndex >= input.length
    }

    fun exploreRegexInfo(curentRegex: Int, currentMatchMode: MatchMode): RegexInfo? {
        if (curentRegex >= regex.length) {
            return RegexInfo(currentMatchMode, invalid, arrayListOf(invalid))
        }
        val curRegexByte = regex[curentRegex].toByte()
        when (currentMatchMode) {
            MatchMode.None -> {
                //根据regex下一位去探索regex的模式
                if (curRegexByte == point) {
                    return exploreRegexInfo(curentRegex + 1, MatchMode.AnyChar)
                } else if (curRegexByte == star) {
                    //题干说明*前面必有字符，这个情况不存在
                    println("error : it should take a char before *")
                    return null
                } else {
                    val result = exploreRegexInfo(curentRegex + 1, MatchMode.SpecificChar)
                    result?.matchChar = curRegexByte
                    return result
                }
            }
            MatchMode.SpecificChar -> {
                //根据下一位判断是匹配一个确定值还是 零或多个确定值
                if (curRegexByte == star) {
                    return exploreRegexInfo(curentRegex + 1, MatchMode.SpecificCharUntil)
                } else {
                    return RegexInfo(MatchMode.SpecificChar, invalid, arrayListOf(invalid))
                }
            }
            MatchMode.AnyChar -> {
                //根据下一位判断是匹配一个任何值还是 零或多个任何值
                if (curRegexByte == star) {
                    return exploreRegexInfo(curentRegex + 1, MatchMode.AnyCharUntil)
                } else {
                    return RegexInfo(MatchMode.AnyChar, any, arrayListOf(invalid))
                }
            }
            MatchMode.AnyCharUntil,
            MatchMode.SpecificCharUntil -> {
                //返回匹配模式相关信息
                if (curRegexByte == point) {
                    return RegexInfo(currentMatchMode, any, arrayListOf(any))
                } else if (curRegexByte == star) {
                    //题干说明*前面必有字符，这个情况不存在
                    println("error : it should take a char before *")
                    return null
                } else {
                    val nextRegexResult = exploreRegexInfo(curentRegex + 1, MatchMode.SpecificChar)
                    if (nextRegexResult?.matchMode == MatchMode.SpecificChar) {
                        return RegexInfo(currentMatchMode, if (MatchMode.SpecificCharUntil == currentMatchMode) invalid else any, arrayListOf(curRegexByte))
                    } else if (nextRegexResult?.matchMode == MatchMode.SpecificCharUntil) {
                        return RegexInfo(currentMatchMode, if (MatchMode.SpecificCharUntil == currentMatchMode) invalid else any, arrayListOf(curRegexByte).apply { addAll(nextRegexResult.matchEndChar) })
                    } else {
                        return null
                    }
                }
            }
        }
    }

    class RegexInfo(var matchMode: MatchMode, var matchChar: Byte, var matchEndChar: ArrayList<Byte>) {

    }

    enum class MatchMode {
        None,
        SpecificChar,
        AnyChar,
        SpecificCharUntil,
        AnyCharUntil
    }
}
//leetcode submit region end(Prohibit modification and deletion)
