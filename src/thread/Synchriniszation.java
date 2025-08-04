package thread;

import java.util.*;

public class Synchriniszation {

    public static void main(String[] args) {
        allCollectionSynchrinizedConceptt();
    }

    private static void allCollectionSynchrinizedConceptt() {


        Vector<Integer> syncroNizedVector = new Vector<>();

        Hashtable<Integer, Integer> synHashTable = new Hashtable<>();

        Stack<Integer> synStack = new Stack<>();

        // making a list a synchrinized list

        List<String> syncList = Collections.synchronizedList(new ArrayList<>());

        // now creating sync map

        Map<Integer, Integer> synMap = Collections.synchronizedMap(new HashMap<>());

        /**
         * all collections that are synchronized and thread   safe
         * Vector
         * Hashtable
         * Stack
         * Collections.synchronizedList()
         * Collections.synchronizedMap()
         * Collections.synchronizedSet()
         * Collections.synchronizedSortedSet()
         * Collections.synchronizedNavigableSet()
         * Collections.synchronizedCollection()
         *
         */

        // now all the concurrent collections

        /**
         * ConcurrentHashMap
         * ConcurrentSkipListMap
         * ConcurrentSkipListSet
         * ConcurrentLinkedQueue
         * ConcurrentLinkedDeque
         * CopyOnWriteArrayList
         * CopyOnWriteArraySet
         */


        // all Blocking collections

        /**
         * Blocking Collections
         * These are used to solve the producer consumer problem
         * BlockingQueue
         * LinkedBlockingQueue
         * ArrayBlockingQueue
         * PriorityBlockingQueue
         * DelayQueue
         * SynchronousQueue
         *
         */



    }
}
