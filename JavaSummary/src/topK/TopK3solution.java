package topK;

import java.util.*;

public class TopK3solution {
    public static void main(String[] args) throws Exception {
        // System.out.println(maxProfit(new int[]{1,2,3,0,2}));
        Integer[] a = {1, 3, 3, 2, 4, 6};

        System.out.println(Arrays.toString(cutk(a, 3)));

        heapk(a, 4);

        List<Integer> nums = new ArrayList<Integer>(Arrays.asList(a));
        System.out.println(qselect(nums, 2));
    }

    //sort arrays cut k
    public static Integer[] cutk(Integer[] a, int k) {
        Arrays.sort(a, ((o1, o2) -> o2 - o1));
        Integer[] res = Arrays.copyOfRange(a, 0, k);
        return res;
    }

    //min heap
    public static void heapk(Integer[] a, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(k, ((o1, o2) -> o1 - o2));
        for (Integer i : a) {
            if (pq.size() < k) {
                pq.add(i);
            } else {
                if (i.compareTo(pq.peek())>0){
                    pq.poll();
                    pq.add(i);
                }
            }
        }
        while (pq.peek() != null) {
            System.out.println(pq.poll());
        }
    }

    //Quick select O(n)
    public static List<Integer> qselect(List<Integer> nums, int k) {
        List<Integer> list = new ArrayList<>();
        if (nums.size() < k) {
            return list;
        }
        Random random = new Random();
        int index = random.nextInt(nums.size() - 1);
//        index = nums.size() - 1;
        for (int num : nums) {
            if (num >= nums.get(index)) {
                list.add(num);
            }
        }
        if (list.size() == k) {
            return list;
        } else if (list.size() > k) {
            return qselect(list, k);
        } else {
            List<Integer> left = new ArrayList<>();
            for (Integer num : nums) {
                if (num < nums.get(index)) {
                    left.add(num);
                }
            }
            left = qselect(left, k - list.size());
            list.addAll(left);
            return list;
        }
    }


}
