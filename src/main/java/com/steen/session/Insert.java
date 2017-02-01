package com.steen.session;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import static com.steen.Main.connection;

/**
 * Created by Lennard Kras on 24-1-2017.
 */

public class Insert {
    private String TableName;
    private ArrayList<String> Columns;
    private ArrayList<ArrayList<String>> Records;
    private String ConstructedSQL;

    public Insert(String TableName, ArrayList<String> Columns) {
        this.TableName = TableName;
        this.Columns = Columns;
        this.Records = new ArrayList<ArrayList<String>>();
    }

    /**
     *
     * @param record Each record must contain all attributes that are present listed given in the columns.
     */
    public void addRecord(ArrayList<String> record) {
        try{
            LengthViolationCheck(record);
            Records.add(record);
        } catch (IllegalArgumentException IA) {
            IA.printStackTrace();
        }
    }

    public void clearRecords(){
        Records = new ArrayList<ArrayList<String>>();
    }

    public int executeQuery() {
        if(Records.size() == 0){
            return 0;
        }
        int affectedRows = 0;

        try{
            ConstructSQL();
            PreparedStatement myStmt = connection.prepareStatement(ConstructedSQL);
            affectedRows = myStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    private void LengthViolationCheck(ArrayList<String> record) throws IllegalArgumentException {
        if(record.size() != Columns.size()){
            throw new IllegalArgumentException("Amount of attributes in this record did not equal the amount of columns.");
        }
    }

    private void ConstructSQL(){
        ConstructedSQL = "INSERT INTO " + TableName + " " + ConstructColumnString() + " VALUES " +  ConstructValuesString() + ";";
    }

    private String ConstructColumnString() {
        String baseString = "(";
        int cnt = 1;
        for(String Column : Columns) {
            baseString = (cnt == Columns.size()) ?  baseString + Column : baseString + Column + ",";
            cnt++;
        }
        baseString = baseString + ")";
        return baseString;
    }

    private String ConstructValuesString() {
        String baseString = "";
        int cnt = 1;
        for(ArrayList<String> record : Records){
            baseString = baseString + "(";
            int atCnt = 1;
            for(String attribute : record) {
                baseString = (atCnt != record.size()) ? baseString + attribute + "," : baseString + attribute;
                atCnt++;
            }
            baseString = (cnt == record.size()) ? baseString + ")," : baseString + ")";
            cnt++;
        }
        return baseString;
    }
}