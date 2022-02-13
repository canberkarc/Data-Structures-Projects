import java.util.*;

abstract interface SLNavigableSet<E extends Comparable<E>> {
    public boolean add(E item);

    public E remove(E item);

    public Iterator<E> descendingIterator();
}

public class SkipListNavigableSet<E extends Comparable<E>> extends SkipList<E> implements SLNavigableSet<E> {

    /** skiplist */
    SkipList<E> list = new SkipList<E>();

    /** adds item
    * @param item
    * @return added value
    */
    public boolean add(E item) {
        boolean added = list.add(item);
        return added;
    }

    /** removes item
    * @param item
    * @return removed one
    */
    public E remove(E item) {
        E removed = list.remove(item);
        return removed;
    }

    /**
    * descending iterator
    */
    public Iterator<E> descendingIterator() {
        return list.descIterator();
    }

    /** display
    */
    public void display(){
        System.out.println(list.toString());
    }

}
