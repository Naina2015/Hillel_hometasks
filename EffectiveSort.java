import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by natali on 6/1/2017.
 */


public class EffectiveSort {
    public static void main(String[] args) {

       int[] UnsortedArray=new int[]{ 1,17,22,11,18,5,0,10,3,4,20,8,6,13,-5 };
       int[] sortedArray=new int[UnsortedArray.length];
        HashSet<Integer> myHashSet = new HashSet<Integer>();

        for(int i=0;i<UnsortedArray.length;i++){
            myHashSet.add(UnsortedArray[i]);
        }

        TreeSet myTreeSet = new TreeSet();
        myTreeSet.addAll(myHashSet);


        Iterator<Integer> it = myTreeSet.iterator();

        Integer current = 0;
        int j = sortedArray.length;
        while(it.hasNext() ) {
            current = it.next();
            sortedArray[--j]=current;
        }

        for(int i=0;i<UnsortedArray.length;i++) {

            System.out.println(sortedArray[i]);
        }
    }
}
