import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int arraySize;
    private Item[] elements;
    private int numElements;


    public RandomizedQueue() {
        this.arraySize = 1;
        this.elements = (Item[]) new Object[arraySize];
        this.numElements = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return numElements;
    }

    public void enqueue(Item item) {
        validateItem(item);
        if (isFull()) duplicateSize();
        elements[size()] = item;
        numElements++;

    }

    private boolean isFull() {
        return size() == arraySize;
    }

    private boolean isAlmostEmpty() {
        return size() == arraySize / 4;
    }

    private void duplicateSize() {
        resize(2 * arraySize);
    }

    public Item dequeue() {
        validateNotEmpty();
        Item itemToReturn = removeRandomItemAndReorder();
        if (isAlmostEmpty()) halfSize();
        return itemToReturn;
    }

    private int randomIndex() {
        return StdRandom.uniform(size());
    }

    private void halfSize() {
        resize(arraySize / 2);
    }

    private Item removeRandomItemAndReorder() {
        int index = randomIndex();
        int lastIndex = size() - 1;
        Item item = elements[index];
        elements[index] = elements[lastIndex];
        elements[lastIndex] = null;
        numElements--;
        return item;
    }

    private void resize(int capacity) {
        if (isEmpty()) {
            return;
        }
        arraySize = capacity;
        Item[] copy = (Item[]) new Object[arraySize];
        int maxSize = size();
        for (int i = 0; i < maxSize; i++) {
            copy[i] = elements[i];
        }
        elements = copy;

    }


    public Item sample() {
        validateNotEmpty();
        return elements[randomIndex()];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final RandomizedQueue<Item> copy;

        RandomizedQueueIterator() {
            copy = new RandomizedQueue<Item>();
            for (int i = 0; i < size(); i++) {
                copy.enqueue(elements[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return copy.size() > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("There is no more elements");
            return copy.dequeue();

        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported by this class");

        }
    }

    private void validateItem(Item item) {
        if (item == null) throw new IllegalArgumentException("The item can't be null");
    }

    private void validateNotEmpty() {
        if (isEmpty()) throw new NoSuchElementException("The queue is empty");
    }

    public static void main(String[] args) {
    }
}
