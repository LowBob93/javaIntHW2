import java.util.*;

public class CustomArrayList<T>  implements CustomList<T> {
    private int size = 0;
    private int capacity = 10;
    private Object[] array;

    public CustomArrayList(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
            array = new Object[capacity];
        } else {
            throw new IllegalArgumentException("Invalid capacity");
        }
    }

    public CustomArrayList() {
        array = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    public boolean add(Object object) {
        if (size == capacity) {
            capacity = capacity * 2;
            Object[] tempArray = array;
            array = Arrays.copyOf(tempArray, capacity);
        }
        array[size++] = object;
        return true;
    }


    @Override
    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) array[index];
    }

    @Override
    public T set(int index, T element) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        array[index] = element;
        return element;
    }



    @Override
    public void clear() {
        array = new Object[capacity];
        size = 0;
    }



    @Override
    public T remove( int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T element = (T) array[index];
        size--;
        System.arraycopy(array,index + 1, array, index, size - index);
        return element;
    }


    @Override
    public String toString() {
        StringBuilder tempBuilder = new StringBuilder();
        for(int i = 0; i < size; i++){
            tempBuilder.append(array[i]);
            if(i != size - 1){
                tempBuilder.append(", ");
            }
        }
        return "MyArrayList{" +
                tempBuilder.toString() +
                '}';
    }

    private class ArrayIterator implements Iterator<T>{
        int position = 0;

        @Override
        public boolean hasNext() {
            return position != size;
        }

        @Override
        public T next() {
            return (T) array[position++];
        }
    }
    @Override
    public void add( int index, Object element) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        size++;
        if (size == capacity) {
            capacity = capacity * 2;
            Object[] tempArray = array;
            array = Arrays.copyOf(tempArray, capacity);
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
    }

}

