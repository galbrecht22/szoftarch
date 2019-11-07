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
    public Pack createPack(Map<String, String> map){
        int ID = Integer.parseInt((map.get("ID")));
        double v = Double.parseDouble((map.get("volume")));
        double m = Double.parseDouble((map.get("mass")));
        Float from_lat = Float.parseFloat((map.get("from_lat")));
        Float from_lon = Float.parseFloat((map.get("from_lon")));
        Float to_lat = Float.parseFloat((map.get("to_lat")));
        Float to_lon = Float.parseFloat((map.get("to_lon")));
        float[] from = new float[]{from_lat, from_lon};
        float[] to = new float[]{to_lat, to_lon};
        Integer year = Integer.parseInt((map.get("year")));
        Integer month = Integer.parseInt((map.get("month")));
        Integer day = Integer.parseInt((map.get("day")));
        LocalDate d = LocalDate.of(year, month, day);
        Pack p = new Pack(v, m, from, to, d);
        p.setID(ID);
        return p;
    }
}
