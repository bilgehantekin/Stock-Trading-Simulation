public class Queue <E>  {
    private E[] data;
    private int f = 0;
    private int sz = 0;

    public Queue() {
        data = (E[]) new Object[1000];
    }

    public int size() {
        return sz;
    }
    public boolean isEmpty() {
        return sz == 0;
    }
    public void enqueue(E e) throws IllegalStateException {
        if(sz == data.length) {
            throw new IllegalStateException("Queue is full");
        }
        int avail = (f + sz) % data.length;
        data[avail] = e;
        sz++;
    }
    public E peek() {
        if(isEmpty()) {
            return null;
        }
        return data[f];
    }
    public E dequeue() {
        if(isEmpty()) {
            return null;
        }
        E answer = data[f];
        data[f] = null;
        f = (f + 1) % data.length;
        sz--;
        return answer;
    }
    public void print() {
        for (E datum : data) {
            if(datum != null) {
                System.out.println(datum);
            }
        }
        System.out.println("f: " + f);
        System.out.println("sz: " + sz);
    }
}
