import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by natali on 6/1/2017.
 */


public class EffectiveSort {
    public static void main(String[] args) {

       int[] UnsortedArray=new int[]{ 1,17,22,11,18,5,0,10,3,4,20,8,6,13,-5 };

        HashSet<Integer> myHashSet = new HashSet<Integer>();

        for(int i=0;i<UnsortedArray.length;i++){
            myHashSet.add(UnsortedArray[i]);
        }

        TreeSet myTreeSet = new TreeSet();
        myTreeSet.addAll(myHashSet);
        System.out.println(myTreeSet);
    }
}
