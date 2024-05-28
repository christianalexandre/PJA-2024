class Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val targetNums = IntArray(2)
        for(i in nums.indices)
        {
            for(j in nums.indices)
            {
                if(nums[i]+nums[j] == target && j != i)
                {
                    targetNums.set(0, i)
                    targetNums.set(1, j)
                    return targetNums
                }
            }
        }
        return targetNums
    }
}