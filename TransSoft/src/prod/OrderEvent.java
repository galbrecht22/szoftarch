package prod;

import java.util.HashMap;
import java.util.Map;

public class OrderEvent {
    private OrderEventEnum orderEventEnum;

    private Map<String, String> event = new HashMap<>();

    public void setOrderEventEnum(OrderEventEnum oee){
        this.orderEventEnum = oee;
    }

    public OrderEventEnum getOrderEventEnum(){
        return orderEventEnum;
    }

    public void setEvent(Map<String, String> event) {
        this.event = event;
    }

    public Map<String, String> getEvent() {
        return event;
    }
}
