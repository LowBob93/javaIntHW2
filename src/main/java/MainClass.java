import java.util.Iterator;

public class MainClass {
    public static void main(String[] args) {
        arrayListTest();
        linkedListTest();
    }

    private static void arrayListTest(){
        CustomArrayList<Integer> list = new  CustomArrayList<>(2);
        list.add(35);
        list.add(25);
        list.add(17);
        list.add(40);
        list.add(4);
        list.add(5);
        System.out.println(list);
        list.remove(1);
        System.out.println(list);
        list.add(1, 33);
        System.out.println(list);
        System.out.println(list.size());
        final Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    private static void linkedListTest(){
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.add(5);
        list.add(42);
        list.add(17);
        list.add(15);
        list.add(36);
        list.add(11);
        System.out.println(list);
        list.remove(1);
        System.out.println(list);
        list.add(1, 19);
        System.out.println(list);
        System.out.println(list.size());
        final Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}

