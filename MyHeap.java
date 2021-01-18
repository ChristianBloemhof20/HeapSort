/** Christian Bloemhof
 *  CS 349-05
 *  Creating a heap from an array of integers
 */

import java.util.ArrayList;
import java.util.Arrays;

public class MyHeap {

    private int capacity, size;
    private ArrayList<Integer> array;

    public MyHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.array = new ArrayList<Integer> ();

        /** We are going to place a 0 at the 0 index because we want the heap to begin at 1 */
        this.array.add(0);
    }

    public boolean buildHeap(ArrayList<Integer> array_of_int) {
        /** If we can put create the heap, then copy all values from the array into our heap and
         *  heapify bottom up (Drift Up).
         */
        if (array_of_int.size() > this.capacity) {
            return false;
        }
        for (int i = 1; i <= array_of_int.size(); i++) {
            this.array.add(i, array_of_int.get(i-1));
            this.size++;
        }

        for (int i = this.size/2; i >= 1; i--) {
            int parent = i;
            boolean heap = false;
            while (!heap && 2*parent <= this.size) {
                int child = 2*parent;
                if (child < this.size) {
                    if (this.array.get(child) > this.array.get(child+1)) {
                        child = child + 1;
                    }
                }
                if (this.array.get(parent) < this.array.get(child)) {
                    heap = true;
                }
                else {
                    int temp = this.array.get(parent);
                    this.array.set(parent, this.array.get(child));
                    this.array.set(child, temp);
                    parent = child;
                }
            }
        }
        return true;
    }

    public int findMin() {
        /** Get the first element of the Min Heap */
        return this.array.get(1);
    }

    public int deleteMin() {
        int min = this.array.get(1);
        int last_val = this.array.get(size);
        this.array.set(1, last_val);
        this.array.remove(size);
        size--;
        if (this.getHeapSize() > 0) {
            driftDown(1);
        }
        return min;
    }

    public boolean insert(int x) {
        /** Insert the new element at the last possible location and drift up */
        if (this.size == this.capacity) {
            return false;
        }
        size++;
        this.array.add(x);
        driftUp(this.size);
        return true;
    }

    public void driftUp(int index) {
        int value = this.array.get(index);
        while (index > 1 && value < this.array.get(index/2)) {
            int parent = this.array.get(index/2);
            this.array.set(index, parent);
            index = index / 2;
        }
        this.array.set(index, value);
    }

    public void driftDown(int index) {
        int tmp = this.array.get(index);
        while (index * 2 <= this.size) {
            int child = index * 2;
            if (child != this.size && (this.array.get(child)) > this.array.get(child+1)) {
                child++;
            }
            if (this.array.get(child) < tmp) {
                this.array.set(index, this.array.get(child));
                index = child;
            }
            else {
                break;
            }
        }
        this.array.set(index, tmp);
    }

    public boolean isEmpty() {
        if (this.size >= 1) {
            return false;
        }
        return true;
    }

    public boolean isFull() {
        if (this.size == this.capacity) {
            return true;
        }
        return false;
    }

    private int getHeapSize() {
        return this.size;
    }

    private int getHeapCap() {
        return this.capacity;
    }

    public static int[] heapSortDecreasing(int[] a) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        for (int i=0; i < a.length; i++) {
            array.add(a[i]);
        }
        MyHeap heap = new MyHeap(50);
        heap.buildHeap(array);

        for (int i=0; heap.getHeapSize() >= 1; i++) {
            a[i] = heap.deleteMin();
        }

        return a;
    }
}