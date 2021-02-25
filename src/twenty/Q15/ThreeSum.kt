package twenty.Q15

import java.util.*
import kotlin.collections.HashSet
import kotlin.test.assertEquals

//给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重
//复的三元组。
//
// 注意：答案中不可以包含重复的三元组。
//
//
//
// 示例 1：
//
//
//输入：nums = [-1,0,1,2,-1,-4]
//输出：[[-1,-1,2],[-1,0,1]]
//
//
// 示例 2：
//
//
//输入：nums = []
//输出：[]
//
//
// 示例 3：
//
//
//输入：nums = [0]
//输出：[]
//
//
//
//
// 提示：
//
//
// 0 <= nums.length <= 3000
// -105 <= nums[i] <= 105
//
// Related Topics 数组 双指针
// 👍 2991 👎 0
fun main(args: Array<String>) {
    var timeStart = System.nanoTime()

    assertEquals(listOf(listOf(-4, 0, 4), listOf(-4, 1, 3), listOf(-2, -1, 3), listOf(-2, 0, 2), listOf(-1, -1, 2), listOf(-1, 0, 1), listOf(0, 0, 0)), Solution().threeSum(intArrayOf(-1, 0, 1, 2, -1, -4, 4, 3, -2, 0, 0)))
    assertEquals(listOf(listOf(-1, -1, 2), listOf(-1, 0, 1)), Solution().threeSum(intArrayOf(-1, 0, 1, 2, -1, -4)))
    assertEquals(listOf(listOf(-4, 0, 4), listOf(-1, -1, 2), listOf(-1, 0, 1)), Solution().threeSum(intArrayOf(-4, -1, 0, 1, 2, -1, 4)))
    assertEquals(listOf(), Solution().threeSum(intArrayOf()))
    assertEquals(listOf(), Solution().threeSum(intArrayOf(0)))
    println("success --- ${System.nanoTime() - timeStart}---")
    ///37714403
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    fun threeSum(nums: IntArray): List<List<Int>> {
        val pool = hashMapOf<Int, Int>()
        val positive = TreeSet<Int>()
        val navigative = TreeSet<Int>()
        val result = arrayListOf<List<Int>>()
        var zeroNumber = 0
        var resultCache = HashSet<String>()
        var index = 0
        while (index < nums.size) {
            pool.put(nums[index], (pool[nums[index]] ?: 0) + 1)
            if (nums[index] > 0) {
                positive.add(nums[index])
            } else if (nums[index] == 0) {
                zeroNumber++
            } else {
                navigative.add(nums[index])
            }
            index++
        }
        for (naviValue in navigative) {
            val posiIte = positive.descendingIterator()
            while (posiIte.hasNext()) {
                val posiValue = posiIte.next()
                val residue = 0 - (naviValue + posiValue)
                val thirdCount = pool[residue]
                if (thirdCount != null) {
                    if ((residue != naviValue && residue != posiValue) || thirdCount > 1) {
                        val min: Int
                        val mid: Int
                        val max: Int
                        if (residue < 0) {
                            max = posiValue
                            if (naviValue < residue) {
                                min = naviValue
                                mid = residue
                            } else {
                                mid = naviValue
                                min = residue
                            }
                        } else {
                            min = naviValue
                            if (posiValue > residue) {
                                max = posiValue
                                mid = residue
                            } else {
                                max = residue
                                mid = posiValue
                            }
                        }
                        val key = "" + min + mid + max
                        if (!resultCache.contains(key)) {
                            resultCache.add(key)
                            result.add(listOf(min, mid, max))
                        }
                    }
                }
            }
        }
        if (zeroNumber >= 3) {
            result.add(listOf(0, 0, 0))
        }
        return result
    }
}
//leetcode submit region end(Prohibit modification and deletion)