class Solution {
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {

        //Sexta função: list1.val: null list1.next: null, logo list1 == null
        if(list1 == null)
        {
            //Sexta função: var: 4 next: null
            return list2
        }
        
        if(list2 == null)
        {
            return list1
        }

        //Primera função: 1 <= 1
        //Terceira função: 2 <= 3
        //Quinta função: 4 <= 4
        if(list1.`val` <= list2.`val`)
        {
            // Primeira função: val: 2 next: 4         //val: 2 next: 4, val: 1 next 3
            // Terceira função: val: 4 next: null      //val: 4 next: null , val: 3 next: 4
            // Quinta função: val: null next: null     //val: null next: null , val: 4 next: null 
            list1.next = mergeTwoLists(list1.next, list2) 
            return list1
        } 

        //Segunda função: 2 >= 1
        //Quarta função: 4 >= 3
        else
        {
            // Segunda função: val: 3 next: 4          //val: 2 next: 4, val: 3 next: 4
            // Quarta função: val: 4 next: null        //val: 4 next: null, val: 4 next: null 
            list2.next = mergeTwoLists(list1, list2.next)
            return list2
        }
    }
}