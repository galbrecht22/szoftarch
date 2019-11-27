package prod;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class ActivationQueue {
    private Queue<Order> queue = new ArrayDeque<>();

    public void addOrder(Order o){
        queue.add(o);
    }

    public Queue<Order> getOrders(){
        return queue;
    }
}
