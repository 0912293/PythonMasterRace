package com.steen.session;
import java.util.ArrayList;

public class OrderBy {
    ArrayList<String> orders = new ArrayList<>();

    public void addParameter(String column){
        orders.add(column);
    }

    public String getOrderByStatement(){
        StringBuilder sb = new StringBuilder();
        if(orders.size() > 0){
            sb.append("ORDER BY ");
            for (int i = 0; i < orders.size(); i++) {
                sb.append(orders.get(i));
                if (!(i+1 == orders.size())) {
                    sb.append(" , ");
                }
            }
        }
        return sb.toString();
    }
}
