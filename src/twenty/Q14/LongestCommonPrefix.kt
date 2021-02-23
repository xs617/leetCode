package twenty.Q14

import java.lang.StringBuilder
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.test.assertEquals

//编写一个函数来查找字符串数组中的最长公共前缀。
//
// 如果不存在公共前缀，返回空字符串 ""。
//
//
//
// 示例 1：
//
//
//输入：strs = ["flower","flow","flight"]
//输出："fl"
//
//
// 示例 2：
//
//
//输入：strs = ["dog","racecar","car"]
//输出：""
//解释：输入不存在公共前缀。
//
//
//
// 提示：
//
//
// 0 <= strs.length <= 200
// 0 <= strs[i].length <= 200
// strs[i] 仅由小写英文字母组成
//
// Related Topics 字符串
// 👍 1465 👎 0

fun main(args: Array<String>) {
    var timeStart = System.nanoTime()

    assertEquals("fl", Solution().longestCommonPrefix(arrayOf("flower", "flow", "flight")))
    assertEquals("", Solution().longestCommonPrefix(arrayOf("dog", "racecar", "car")))
    assertEquals("de", Solution().longestCommonPrefix(arrayOf("decent ", "descent", "descend")))
    assertEquals("decent", Solution().longestCommonPrefix(arrayOf("decent")))
    assertEquals("", Solution().longestCommonPrefix(arrayOf("")))
    assertEquals("abcde", Solution().longestCommonPrefix(arrayOf("abcde","abcde","abcde")))
    println("success --- ${System.nanoTime() - timeStart}---")
    ///37714403
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    fun longestCommonPrefix(inputs: Array<String>): String {
        if (inputs.isEmpty()) {
            return ""
        } else if (inputs.size == 1) {
            return inputs[0]
        } else {
            var longestCommonPrefix = StringBuilder()
            var minLength = Int.MAX_VALUE
            var index = 0
            var indexValue: Char
            inputs.forEach {
                if (it.length < minLength) {
                    minLength = it.length
                }
            }
            while (index < minLength) {
                indexValue = inputs[0][index]
                inputs.forEach {
                    if (it[index] != indexValue) {
                        return longestCommonPrefix.toString()
                    }
                }
                longestCommonPrefix.append(indexValue)
                index++
            }
            return longestCommonPrefix.toString()
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)


//fun longestCommonPrefix(inputs: List<String>): String {
//    var commonPrefixMap = HashMap<String, ArrayList<String>>()
//    commonPrefixMap.put("", ArrayList(inputs))
//    var longestCommonPrefix = ""
//    while (commonPrefixMap.isNotEmpty()) {
//        val nextCommonPrefixMap = HashMap<String, ArrayList<String>>()
//        for (commonPrefixKey in commonPrefixMap.keys) {
//            val index = commonPrefixKey.length
//            val lastCommList = commonPrefixMap[commonPrefixKey]
//            if (lastCommList!!.size > 1) {
//                longestCommonPrefix = if (longestCommonPrefix.length > commonPrefixKey.length) longestCommonPrefix else commonPrefixKey
//                for (input in lastCommList) {
//                    if (input.length > index + 1) {
//                        val commPrefix = input.substring(0, index + 1)
//                        var commonList = nextCommonPrefixMap[commPrefix]
//                        if (commonList == null) {
//                            commonList = ArrayList()
//                        }
//                        commonList.add(input)
//                        nextCommonPrefixMap.put(commPrefix, commonList)
//                    }
//                }
//            }
//        }
//        commonPrefixMap = nextCommonPrefixMap
//    }
//    return longestCommonPrefix
//}