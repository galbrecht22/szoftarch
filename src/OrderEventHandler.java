public class OrderEventHandler {

    private Dispatcher dispatcher = Dispatcher.getInstance();

    public void handle(OrderEvent oe){
        switch (oe.getOrderEventEnum()){
            case NEW_ORDER:
                dispatcher.onPackReceived(oe.getEvent());
                break;
            case COMPUTE_REQUEST:
                dispatcher.onComputeRequestReceived();
                break;
        }
    }
}
