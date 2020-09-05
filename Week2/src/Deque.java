import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{

    private Node first;
    private Node last;
    private int count;

    private class Node{
        Node next;
        Node prev;
        Item item;

        Node(Node next, Node prev, Item item) {
            this.next = next;
            this.prev = prev;
            this.item = item;
        }

        Node() {
        }
    }

    public Deque(){
        this.first = null;
        this.last = null;
        this.count = 0;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public int size(){
        return count;
    }

    public void addFirst(Item item){
        validateItem(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;


        if(isEmpty()){
            last = first;
        }
        else{
            oldFirst.prev = first;
        }
        count++;

    }

    public void addLast(Item item){
        validateItem(item);

        Node oldLast =last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if(isEmpty()) first = last;
        else{
            oldLast.next = last;
        }
        count++;


    }

    public Item removeFirst(){
        validateNotEmpty();
        Node oldFirst = first;
        Item returnItem = oldFirst.item;
        first = oldFirst.next;
        oldFirst.next = null;
        first.prev = null;
        count--;
        return returnItem;

    }

    public Item removeLast(){
        validateNotEmpty();

        Node oldLast = last;
        Item returnItem = oldLast.item;
        last = last.prev;
        count--;
        last.next = null;
        oldLast.prev = null;
        return returnItem;

    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>{
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException("There are no more element");
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove(){
            throw new UnsupportedOperationException("This operation is not supported by this class");
        }
    }

    private void validateItem(Item item){
        if(item == null) throw new IllegalArgumentException("The item can't be null");
    }

    private void validateNotEmpty(){
        if(isEmpty()) throw new NoSuchElementException("The deque is empty");
    }

    public static void main(String args[]){
        Deque<Integer> deck = new Deque<Integer>();

        System.out.println("IS EMPTY: " + deck.isEmpty());

        for (int i = 0; i < 10; i++) {
            deck.addFirst(i);
            System.out.println("SIZE: " + deck.size());
            System.out.println("IS EMPTY: " + deck.isEmpty());
        }

        System.out.println("Elements 0-9 added. We should have seen 1 to 10 printed");

        for (Integer i : deck) {
            System.out.println(i);
        }

        System.out.println("Finished iterating over the iterator. Elements should appear from 9 to 0.");

        for (int i = 0; i < 10; i++) {
            System.out.println(deck.removeLast());
            System.out.println("IS EMPTY: " + deck.isEmpty());
            System.out.println("Deck size: " + deck.size());
        }

        System.out.println("Elements 0-9 removed. They should appear from 0 to 9.");

        for (int i = 0; i < 10; i++) {
            deck.addLast(i);
            System.out.println("IS EMPTY: " + deck.isEmpty());
            System.out.println("Deck size: " + deck.size());
        }

        System.out.println("Elements 0-9 added.");

        for (Integer i : deck) {
            System.out.println(i);
        }

        System.out.println("Finished iterating over the iterator. Elements should appear from 0 to 9");

        for (int i = 0; i < 10; i++) {
            System.out.println(deck.removeFirst());
            System.out.println("IS EMPTY: " + deck.isEmpty());
            System.out.println("Deck size: " + deck.size());
        }

        System.out.println("Elements 0-9 removed. Elements should appear from 0 to 9");

    }
}
