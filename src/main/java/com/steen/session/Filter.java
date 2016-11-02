package com.steen.session;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Lucas on 31-10-2016.
 */
public class Filter {
    ArrayList<String> filters = new ArrayList<>();

    public Filter(String... filters){
        this.filters.addAll(Arrays.asList(filters));
    }

    public void addParameter(String param) {
        if (filters.contains(param)) {
            return;
        }
        filters.add(param);
    }

    public void addParameter(String column, String value, Operator o) {
        StringBuilder sb = new StringBuilder();
        sb.append(column);
        switch (o) {
            case LIKE:
                sb.append(" LIKE ");
                break;
            case NOT_LIKE:
                sb.append(" NOT LIKE ");
                break;
            case NOTEQUAL:
                sb.append(" <> ");
                break;
            case LESSTHEN:
                sb.append(" < ");
                break;
            case LESSTHENEQUAL:
                sb.append(" <= ");
                break;
            case EQUAL:
                sb.append(" = ");
                break;
            case HIGHEREQUAL:
                sb.append(" >= ");
                break;
            case HIGHER:
                sb.append(" > ");
                break;
        }
        sb.append(value);
        if (filters.contains(sb.toString())) return;
        filters.add(sb.toString());
    }

    public void removeParameter(String param) {
        if (filters.contains(param)) {
            filters.remove(param);
        }
    }

    public void removeParameter(int index) {
        try {
            filters.get(index);
            filters.remove(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No parameter at index " + index);
        }
    }

    public ArrayList<String> getParameters() {
        return filters;
    }

    public String getWhereStatement(boolean appendWhere) {
        StringBuilder sb = new StringBuilder();

        if (!(filters.size() > 0)) return "";

        if (appendWhere) sb.append("WHERE ");
        for (int i = 0; i < filters.size(); i++) {
            sb.append(filters.get(i));
            if (!(i+1 == filters.size())) {
                sb.append(" AND ");
            }
        }
        return sb.toString();
    }

    public enum Operator {
        LIKE,
        NOT_LIKE,
        NOTEQUAL,
        LESSTHEN,
        LESSTHENEQUAL,
        EQUAL,
        HIGHEREQUAL,
        HIGHER
    }
}
