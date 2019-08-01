package ListRemove;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddRemoveListElement {
    public static void main(String args[]) {
        List<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");

        for (String s : list) {
            if (s.equals("B")) {
                list.add(s);
                list.remove(s);
            }
        }

        //foreach循环等效于迭代器
        Iterator<String> iterator=list.iterator();
        while(iterator.hasNext()){
            String s=iterator.next();
            if (s.equals("B")) {
                list.remove(s);
            }
        }

        Iterator<String> iter = list.iterator();
        while(iter.hasNext()){
            String str = iter.next();
            if( str.equals("B") )
            {
                iter.remove();
            }
        }
    }
}
