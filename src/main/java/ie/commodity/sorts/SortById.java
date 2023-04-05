package ie.commodity.sorts;

import ie.commodity.Commodity;

import java.util.Comparator;

public class SortById implements Comparator<Commodity> {
        // Used for sorting in ascending order of
        // roll number

    @Override
    public int compare(Commodity o1, Commodity o2) {
        return o1.getId()-o2.getId();
    }
}
