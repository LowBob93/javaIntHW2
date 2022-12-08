import java.util.Iterator;

public class CustomLinkedList <T> implements CustomList<T> {

    private int size = 0;
    private Node<T> first; // ссылка на первый элемент
    private Node<T> last; // ссылка на  последний элемент

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add( Object o) {
        T item = (T) o;
        if (size == 0) {
            Node<T> node = new Node<>(null, item, null);
            first = node;
            last = node;
        } else if (size == 1) {
            Node<T> node = new Node<>(first, item, null);
            last = node;
            first.next = node;
        } else {
            Node<T> node = new Node<>(last, item, null);
            last.next = node;
            last = node;
        }
        size++;
        return true;
    }

    @Override
    public void clear() {
        Node<T> temp = first;
        while (temp != null) {
            Node<T> next = temp.next;
            temp.item = null;
            temp.next = null;
            temp.prev = null;
            temp = next;
        }
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public T get( int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> temp = first;
        T result = null;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                result = temp.item;
                break;
            }
            temp = temp.next;
        }
        return result;
    }

    public T set( int index,  T element) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> temp = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                temp.item = element;
                break;
            }
            temp = temp.next;
        }
        return element;
    }

    @Override
    public T remove( int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> temp = first;
        T result = null;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                result = temp.item;
                Node<T> prev = temp.prev;
                Node<T> next = temp.next;
                next.prev = prev;
                prev.next = next;
                break;
            }
            temp = temp.next;
        }
        size--;
        return null;
    }

    @Override
    public void add( int index,  Object element) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> temp = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                Node<T> newNode = new Node<>(temp.prev, (T)element, temp);
                newNode.next.prev = newNode;
                newNode.prev.next = newNode;
                break;
            }
            temp = temp.next;
        }
        size++;
    }

    public Iterator<T> iterator() {
        return new Iter();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            sb.append(current.item);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        return "MyLinkedList{" +
                sb.toString() +
                '}';
    }


    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private class Iter implements Iterator<T> {

        private Node<T> current = first;

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public T next() {
            T result = current.item;
            current = current.next;
            return result;
        }
    }
}