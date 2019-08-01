package ListRemove;

import java.util.ArrayList;
import java.util.List;

public class ListRemove {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        differRemove(list,3, 3);
    }
    //remove Integer or int
    private static void differRemove(List<Integer> list, int index, int obj) {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println("orgin list is : "+list);
        list.remove(index);
        System.out.println("list remove index 3: "+list );
        Integer curobj=obj;
        list.remove(curobj);
        System.out.println(" list remove obj 3: "+list);
    }
}
