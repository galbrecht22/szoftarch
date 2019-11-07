import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class ActivationQueue {
    private Queue<Pack> queue = new ArrayDeque<>();

    public void addPack(Pack p){
        queue.add(p);
    }

    public Queue<Pack> getPacks(){
        return queue;
    }
}
