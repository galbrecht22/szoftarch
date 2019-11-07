import java.util.Comparator;

public class DateComparator implements Comparator<Pack> {

    @Override
    public int compare(Pack o1, Pack o2) {
        return o1.getWhen().compareTo(o2.getWhen());
    }
}
