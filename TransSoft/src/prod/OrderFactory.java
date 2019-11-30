package prod;

import java.time.LocalDate;
import java.util.Map;

public class OrderFactory {
    private static OrderFactory of;
    public static OrderFactory getInstance(){
        if(of == null){
            of = new OrderFactory();
        }
        return of;
    }
    public Order createOrder(Map<String, String> map){
        //int ID = Integer.parseInt((map.get("ID")));
        final double v = Double.parseDouble((map.get("volume")));
        final double m = Double.parseDouble((map.get("mass")));
        final Float from_lat = Float.parseFloat((map.get("from_lat")));
        final Float from_lon = Float.parseFloat((map.get("from_lon")));
        final Float to_lat = Float.parseFloat((map.get("to_lat")));
        final Float to_lon = Float.parseFloat((map.get("to_lon")));
        final Coordinate from = new Coordinate(from_lat, from_lon);
        final Coordinate to = new Coordinate(to_lat, to_lon);
        final LocalDate d = LocalDate.parse(map.get("date"));
        final Order o = new Order(v, m, from, to, d);
        //o.setID(ID);
        return o;
    }
}