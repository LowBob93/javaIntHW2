import java.util.Iterator;

public interface CustomList <T> extends Iterable<T>{
    void add(final int index, final Object element);
    T remove(final int index);
    T set(final int index, final T element);
    T get(final int index);
    void clear();
    boolean add(final Object o);
    Iterator<T> iterator();
    boolean isEmpty();
    int size();

}