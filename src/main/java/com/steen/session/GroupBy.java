package com.steen.session;

import java.util.ArrayList;

public class GroupBy {
    ArrayList<String> group = new ArrayList<>();

    public void addParameter(String column){
        group.add(column);
    }

    public String getGroupByStatement(){
        StringBuilder sb = new StringBuilder();
        if(group.size() > 0){
            sb.append("GROUP BY ");
            for (int i = 0; i < group.size(); i++) {
                sb.append(group.get(i));
                if (!(i+1 == group.size())) {
                    sb.append(" , ");
                }
            }
        }
        return sb.toString();
    }
}
