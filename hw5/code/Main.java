import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Main {
    public static void main(String args[]) {
        Random rand = new Random();

        System.out.println("*************************** PART1 ***************************\n");

        MapIterator<Integer, Integer> nMap = new MapIterator<>();
        nMap.put(1, 42);
        nMap.put(2, 58);
        nMap.put(3, 1);
        nMap.put(4, 58);
        // Test with no parameter constructor
        MapIterator.MapIter iter = nMap.iterator();
        // Iterate forward
        System.out.format("Next: %d\n", iter.next());
        System.out.format("Next: %d\n", iter.next());
        System.out.format("Next: %d\n", iter.next());
        System.out.format("Next: %d\n", iter.next());
        System.out.format("Next: %d\n", iter.next());
        System.out.format("Next: %d\n", iter.next());
        System.out.format("Next: %d\n", iter.next());
        System.out.println("\n");

        // Iterate backward
        System.out.format("Prev: %d\n", iter.prev());
        System.out.format("Prev: %d\n", iter.prev());
        System.out.format("Prev: %d\n", iter.prev());
        System.out.format("Prev: %d\n", iter.prev());
        System.out.format("Prev: %d\n", iter.prev());
        System.out.format("Prev: %d\n", iter.prev());
        System.out.format("Prev: %d\n", iter.prev());
        System.out.println("\n");

        MapIterator<Integer, Integer> nMap2 = new MapIterator<>(2);
        nMap2.put(1, 42);
        nMap2.put(2, 53);
        nMap2.put(3, 1);
        nMap2.put(4, 58);
        // Test with giving key
        MapIterator.MapIter iter2 = nMap2.iterator();
        // Iterate forward
        System.out.format("Next: %d\n", iter2.next());
        System.out.format("Next: %d\n", iter2.next());
        System.out.format("Next: %d\n", iter2.next());
        System.out.format("Next: %d\n", iter2.next());
        System.out.format("Next: %d\n", iter2.next());
        System.out.format("Next: %d\n", iter2.next());
        System.out.format("Next: %d\n", iter2.next());
        System.out.println("\n");

        // Iterate backwards
        System.out.format("Prev: %d\n", iter2.prev());
        System.out.format("Prev: %d\n", iter2.prev());
        System.out.format("Prev: %d\n", iter2.prev());
        System.out.format("Prev: %d\n", iter2.prev());
        System.out.format("Prev: %d\n", iter2.prev());
        System.out.format("Prev: %d\n", iter2.prev());
        System.out.format("Prev: %d\n", iter2.prev());
        System.out.println("\n");

        System.out.println("Put 10000 elements in hashmap and iterate forward\n");
        MapIterator<Integer, Integer> nMap3 = new MapIterator<>(5000);
        for(int i=0; i<10000; i++){
            int k = i;
            int v = rand.nextInt(1000);
            nMap3.put(k,v);
        }
        MapIterator.MapIter iter3 = nMap3.iterator();
        for(int i=0; i<10; i++){
            System.out.format("Next: %d ",iter3.next());
        }
        System.out.println("\n");

        System.out.println("Iterate backwards\n");
        for (int i=0;i<15; i++){
            System.out.format("Prev: %d\n",iter3.prev());
        }

        System.out.println("\n******************** PART2 ************************\n");

        System.out.println("\n ************Hash table implementation with linked list *************\n");
        HashTableLL<String, Integer> hL = new HashTableLL<>();
        hL.put("Izmir", 1);
        hL.put("Foca", 4);
        hL.put("Buca", 5);
        hL.put("Istanbul", 7);
        hL.put("Adana", 2);
        hL.put("Edirne", 6);
        hL.put("Ankara", 3);
        hL.put("Kirikkale", 8);
        hL.put("Trabzon", 9);
        hL.put("Ordu", 10);
        hL.put("Rize", 11);
        hL.show("Adana");
        System.out.println("\n");
        hL.remove("Buca");
        // **** I tried here when table size is 10 and put results in report then made table size 101
        System.out.println("After deletion of an element from chained elements\n");
        hL.show("Adana");
        System.out.format("\nResult of accessing existent element: %d\n",hL.get("Izmir"));
        System.out.format("\nResult of accessing non-existent element: %d\n",hL.get("aaa"));

        System.out.format("Removed element's value from hash table(linked list): %d\n\n",hL.remove("Rize"));

        HashTableLL<Integer, Integer> hL2 = new HashTableLL<>();
        for(int i=0; i<100; i++){
            int key = rand.nextInt(100);
            int value = rand.nextInt(100);
            hL2.put(key,value);
        }
        System.out.format("\nAccess elements in hash table with 100 elements\n");
        long sTime = System.nanoTime();
        for(int i=0; i<hL2.size(); i++)
            hL2.get(rand.nextInt(100));
        long fTime = System.nanoTime();
        System.out.format("Time to access elements in hash table with 100 elements: %d ns\n",fTime - sTime);

        System.out.format("\nRemove element from hash table with 100 elements\n");
        sTime = System.nanoTime();
        for(int i=0; i<hL2.size(); i++)
            hL2.remove(rand.nextInt(100));
        fTime = System.nanoTime();
        System.out.format("Time to remove element from hash table with 100 elements: %d ns\n",fTime - sTime);

        HashTableLL<Integer, Integer> hL3 = new HashTableLL<>();
        for(int i=0; i<1000; i++){
            int key = rand.nextInt(1000);
            int value = rand.nextInt(1000);
            hL3.put(key,value);
        }
        System.out.format("\nAccess elements in hash table with 1000 elements\n");
        sTime = System.nanoTime();
        for(int i=0; i<hL3.size(); i++)
            hL3.get(rand.nextInt(1000));
        fTime = System.nanoTime();
        System.out.format("Time to access elements in hash table with 1000 elements: %d ns\n",fTime - sTime);

        System.out.format("\nRemove element from hash table with 1000 elements\n");
        sTime = System.nanoTime();
        for(int i=0; i<hL3.size(); i++)
            hL3.remove(rand.nextInt(1000));
        fTime = System.nanoTime();
        System.out.format("Time to remove element from hash table with 1000 elements: %d ns\n",fTime - sTime);

        System.out.println("\nGet element's value from hash table(linked list)");
        System.out.format("Element: %d\n", hL3.get(rand.nextInt(100)));

        System.out.println("See chained elements in same slot with 2");
        hL3.show(2);

        System.out.println("\n **************** Hash table implementation with treeset **************");
        HashTableTree<String, Integer> hT = new HashTableTree<>();
        hT.put("Izmir", 1);
        hT.put("Foca", 4);
        hT.put("Buca", 5);
        hT.put("Istanbul", 7);
        hT.put("Adana", 2);
        hT.put("Edirne", 6);
        hT.put("Ankara", 3);
        hT.put("Kirikkale", 8);
        hT.put("Trabzon", 9);
        hT.put("Ordu", 10);
        hT.put("Rize", 11);
        hT.show("Adana");
        System.out.println("\n");
        hT.remove("Edirne");
        // **** I tried here when table size is 10 and put results in report then made table size 101
        System.out.println("After deletion of an element from chained elements\n");
        hT.show("Adana");
        System.out.format("\nResult of accessing existent element: %d\n",hT.get("Trabzon"));
        System.out.format("\nResult of accessing non-existent element: %d\n",hT.get("aadsgasa"));

        System.out.format("Removed element's value from hash table(treeset): %d\n\n",hT.remove("Ordu"));

        HashTableTree<Integer, Integer> hT2 = new HashTableTree<>();
        for(int i=0; i<100; i++){
            int key = rand.nextInt(100);
            int value = rand.nextInt(100);
            hT2.put(key,value);
        }
        System.out.format("\nAccess elements in hash table with 100 elements\n");
        sTime = System.nanoTime();
        for(int i=0; i<hT2.size(); i++)
            hT2.get(rand.nextInt(100));
        fTime = System.nanoTime();
        System.out.format("Time to access elements in hash table with 100 elements: %d ns\n",fTime - sTime);

        System.out.format("\nRemove element from hash table with 100 elements\n");
        sTime = System.nanoTime();
        for(int i=0; i<hT2.size(); i++)
            hT2.remove(rand.nextInt(100));
        fTime = System.nanoTime();
        System.out.format("Time to remove element from hash table with 100 elements: %d ns\n",fTime - sTime);

        HashTableLL<Integer, Integer> hT3 = new HashTableLL<>();
        for(int i=0; i<1000; i++){
            int key = rand.nextInt(1000);
            int value = rand.nextInt(1000);
            hT3.put(key,value);
        }
        System.out.format("\nAccess elements in hash table with 1000 elements\n");
        sTime = System.nanoTime();
        for(int i=0; i<hT3.size(); i++)
            hT3.get(rand.nextInt(1000));
        fTime = System.nanoTime();
        System.out.format("Time to access elements in hash table with 1000 elements: %d ns\n",fTime - sTime);

        System.out.format("\nRemove element from hash table with 1000 elements\n");
        sTime = System.nanoTime();
        for(int i=0; i<hT3.size(); i++)
            hT3.remove(rand.nextInt(1000));
        fTime = System.nanoTime();
        System.out.format("Time to remove element from hash table with 1000 elements: %d ns\n",fTime - sTime);

        System.out.println("\nGet element's value from hash table(treeset)");
        System.out.format("Element: %d\n", hT3.get(rand.nextInt(100)));

        System.out.println("See chained elements in same slot with 68(treeset)");
        hT3.show(68);

        System.out.println("\n ************** Hash table implementation with Coalesced hashing technique ***************\n");
        HashTableCoa<Integer, Integer> hC = new HashTableCoa<>();
        hC.put(3, 1);
        hC.put(12, 4);
        hC.put(13, 5);
        hC.put(25, 7);
        hC.put(23, 2);
        hC.put(51, 6);
        hC.put(42, 3);
        System.out.println("\n");

        System.out.format("\nResult of accessing existent element: %d\n",hC.get(13));
        System.out.format("\nResult of accessing nonexistent element: %d\n",hC.get(999));
        System.out.println("\n");

        System.out.println("\nGet element's value from hash table(Coalesced hashing technique)");
        System.out.format("Element: %d\n", hC.get(23));

        System.out.println("\n");

        hC.show();
        hC.remove(13);
        hC.remove(25);
        hC.remove(23);
        System.out.println("\nAfter removal: ");
        hC.show();

        System.out.format("\nRemoved element's value from hash table(Coalesced hashing technique): %d\n\n",hC.remove(42));

        HashTableCoa<Integer, Integer> hC2 = new HashTableCoa<>();
        for(int i=0; i<100; i++){
            int key = rand.nextInt(100);
            int value = rand.nextInt(100);
            hC2.put(key,value);
        }
        System.out.format("\nAccess elements in hash table with 100 elements\n");
        sTime = System.nanoTime();
        for(int i=0; i<hC2.size(); i++)
            hC2.get(i);
        fTime = System.nanoTime();
        System.out.format("Time to access elements in hash table with 100 elements: %d ns\n",fTime - sTime);


        System.out.format("\nRemove element from hash table with 100 elements\n");
        sTime = System.nanoTime();
        for(int i=0; i<hC2.size(); i++){
            hC2.remove(rand.nextInt(100));
        }
        fTime = System.nanoTime();
        System.out.format("Time to remove element from hash table with 100 elements: %d ns\n",fTime - sTime);

        HashTableCoa<Integer, Integer> hC3 = new HashTableCoa<>();
        for(int i=0; i<1000; i++){
            int key = rand.nextInt(1000);
            int value = rand.nextInt(1000);
            hC3.put(key,value);
        }
        System.out.format("\nAccess elements in hash table with 1000 elements\n");
        sTime = System.nanoTime();
        for(int i=0; i<hC3.size(); i++)
            hC3.get(i);
        fTime = System.nanoTime();
        System.out.format("Time to access elements in hash table with 1000 elements: %d ns\n",fTime - sTime);

        System.out.format("\nRemove element from hash table with 1000 elements\n");
        sTime = System.nanoTime();
        for(int i=0; i<hC3.size(); i++)
            hC3.remove(rand.nextInt(1000));
        fTime = System.nanoTime();
        System.out.format("Time to remove element from hash table with 1000 elements: %d ns\n",fTime - sTime);

    }
}


