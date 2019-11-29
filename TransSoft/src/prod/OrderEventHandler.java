package prod;

public class OrderEventHandler {

    private Controller controller = Controller.getInstance();

    public void handle(OrderEvent oe){
        switch (oe.getOrderEventEnum()){
            case NEW_ORDER:
                controller.onOrderReceived(oe.getEvent());
                break;
            case COMPUTE_REQUEST:
                controller.onComputeRequestReceived();
                break;
        }
    }
}
