public class DateBuilder {

    StringBuilder sbuilder;

    public void build(String day,String month,String year){
        sbuilder = new StringBuilder();
        sbuilder.append(year+"-"+month+"-"+day);
    }

    public String getDate(){
        return sbuilder.toString();
    }
}
