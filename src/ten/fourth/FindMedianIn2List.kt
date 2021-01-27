package ten.fourth

//给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。
//
// 进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
//
//
//
// 示例 1：
//
// 输入：nums1 = [1,3], nums2 = [2]
//输出：2.00000
//解释：合并数组 = [1,2,3] ，中位数 2
//
//
// 示例 2：
//
// 输入：nums1 = [1,2], nums2 = [3,4]
//输出：2.50000
//解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
//
//
// 示例 3：
//
// 输入：nums1 = [0,0], nums2 = [0,0]
//输出：0.00000
//
//
// 示例 4：
//
// 输入：nums1 = [], nums2 = [1]
//输出：1.00000
//
//
// 示例 5：
//
// 输入：nums1 = [2], nums2 = []
//输出：2.00000
//
//
//
//
// 提示：
//
//
// nums1.length == m
// nums2.length == n
// 0 <= m <= 1000
// 0 <= n <= 1000
// 1 <= m + n <= 2000
// -106 <= nums1[i], nums2[i] <= 106
//
// Related Topics 数组 二分查找 分治算法
// 👍 3625 👎 0
fun main(args: Array<String>) {
//    print(Solution().indexCutPointPosition(simple1, 0, simple1.size - 1, 9))
    println(Solution().findMedianSortedArrays(intArrayOf(1, 3), intArrayOf(2)))
    println(Solution().findMedianSortedArrays(intArrayOf(1, 3), intArrayOf(2, 4)))
    println(Solution().findMedianSortedArrays(intArrayOf(0, 0), intArrayOf(0, 0)))
    println(Solution().findMedianSortedArrays(intArrayOf(), intArrayOf(1)))
    println(Solution().findMedianSortedArrays(intArrayOf(2), intArrayOf()))
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    var nums1: IntArray? = null
    var nums2: IntArray? = null
    //从两个数组中选出第X个数
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        this.nums1 = nums1
        this.nums2 = nums2
        val sum = nums1.size + nums2.size
        if (sum % 2 == 1) {
            println(" ##################### begin #####################")
            return selectValueFromTwoSortList(
                    0, nums1.size,
                    0, nums2.size,
                    sum / 2)
        } else {
            println(" ##################### begin left #####################")
            val left = selectValueFromTwoSortList(
                    0, nums1.size,
                    0, nums2.size,
                    sum / 2 - 1)

            println(" ##################### end left $left #####################")

            println(" ##################### begin right#####################")
            val right = selectValueFromTwoSortList(
                    0, nums1.size,
                    0, nums2.size,
                    sum / 2)

            println(" ##################### end right $right #####################")

            return (left + right) / 2
        }
    }

    fun selectValueFromTwoSortList(rangStart1: Int, rang1Size: Int
                                   , rangStart2: Int, rang2Size: Int
                                   , targetOrder: Int): Double {
        val rangEnd1 = rangStart1 + rang1Size - 1
        val rangEnd2 = rangStart2 + rang2Size - 1

        println(" ---------------------$targetOrder--------------------------")
        println(" a ")
        println(" ${nums1!!.contentToString()} : $rangStart1 : $rang1Size : $rangEnd1")
        println(" ${nums2!!.contentToString()} : $rangStart2 : $rang2Size : $rangEnd2")

        //如果其中一个数组为空，则顺序即为另一个数组顺序
        if (rang1Size == 0) {
            if (rang2Size > targetOrder) {
                return nums2!![rangStart2 + targetOrder].toDouble()
            } else {
                return 0.0
            }
        }
        if (rang2Size == 0) {
            if (rang1Size > targetOrder) {
                return nums1!![rangStart1 + targetOrder].toDouble()
            } else {
                return 0.0
            }
        }
        //如果两个数组都只剩一个，则从这里面选
        if (rang1Size == 1 && rang2Size == 1 && targetOrder < 2) {
            if (targetOrder == 0) {
                return if (nums1!![rangStart1] < nums2!![rangStart2]) nums1!![rangStart1].toDouble() else nums2!![rangStart2].toDouble()
            } else {
                return if (nums1!![rangStart1] > nums2!![rangStart2]) nums1!![rangStart1].toDouble() else nums2!![rangStart2].toDouble()
            }
        }
        val cutPoint2Index = rangStart2 + rang2Size / 2
        val cutPoint2Value = nums2!![cutPoint2Index]
        val cutPoint2IndexIn1 = indexCutPointPosition(nums1!!, rangStart1, rangEnd1, cutPoint2Value)
        val mergeIndex = cutPoint2IndexIn1 - rangStart1 + (cutPoint2Index - rangStart2 + 1) - 1
        println(" b ")
        println(" $cutPoint2Index : $cutPoint2Value : $cutPoint2IndexIn1 : $mergeIndex ")
        if (mergeIndex > targetOrder) {
            return selectValueFromTwoSortList(
                    //插入点并不是数组1的点，所以对应的index在右边
                    rangStart1, cutPoint2IndexIn1 - (rangStart1 + 1) + 1,
                    //插入点已经判断了，不需要再下一次的集合中
                    rangStart2, cutPoint2Index - rangStart2 + 1 - 1,
                    targetOrder
            )
        } else if (mergeIndex == targetOrder) {
            //判断是否为插入点，
            return cutPoint2Value.toDouble()
        } else {
            return selectValueFromTwoSortList(
                    //插入点并不是数组1的点，该点在右边，需要在下一次处理
                    cutPoint2IndexIn1, rangEnd1 - cutPoint2IndexIn1 + 1,
                    //插入点已经处理过，不需要再处理
                    cutPoint2Index + 1, rangEnd2 - (cutPoint2Index + 1) + 1,
                    targetOrder - mergeIndex - 1
            )
        }
    }

    //2,5,5,8,10
    fun indexCutPointPosition(nums: IntArray, rangStart: Int, rangEnd: Int, value: Int): Int {
        if (rangStart >= rangEnd) {
            if (value >= nums[rangStart]) {
                return rangStart + 1
            } else {
                return rangStart
            }
        }
        val middle = rangStart + (rangEnd - rangStart) / 2
        val middleValue = nums[middle]
        if (value > middleValue) {
            if (middle + 1 <= rangEnd && nums[middle + 1] >= value) {
                return middle + 1
            } else {
                return indexCutPointPosition(nums, middle + 1, rangEnd, value)
            }
        } else if (value == middleValue) {
            return middle
        } else {
            if (middle - 1 >= rangStart && value >= nums[middle - 1]) {
                return middle
            } else {
                return indexCutPointPosition(nums, rangStart, middle - 1, value)
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
