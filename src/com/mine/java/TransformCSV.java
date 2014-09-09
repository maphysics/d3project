package com.mine.java;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by missy on 9/4/2014.
 */
public class TransformCSV implements ITransform {

    private static ArrayList<String[]> data = new ArrayList<String[]>();
    private  String sourceFile;
    private  String sinkFile;

    public TransformCSV(){}

    public TransformCSV(String sourceFile, String sinkFile){
        this.sourceFile = sourceFile;
        this.sinkFile = sinkFile;
    }

    public String getSourceFile(){
        return sourceFile;
    }

    public String getSinkFile(){
        return sinkFile;
    }

    public void setSourceFile(String sourceFile){
        this.sourceFile = sourceFile;
    }

    public void setSinkFile(String sinkFile){
        this.sinkFile = sinkFile;
    }

    @Override
    public void load() throws Exception {
        CSVReader reader = new CSVReader(new FileReader(getSourceFile()), ',');
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            data.add(nextLine);
        }
    }

    @Override
    public void save() throws Exception{
        CSVWriter writer = new CSVWriter(new FileWriter(this.sinkFile), ',');
        for(String[] row : data){
            writer.writeNext(row);
        }
        writer.close();
    }

//    @Override
//    public void removeColumn(Integer columnNo, String[] values){
//
//    }

    @Override
    public void addColumn(Integer columnNo, String[] values){
        Integer count = 0;
        for(String[] row : data){
            Integer colNo = 0;
            String[] nRow = new String[row.length+1];
            while(colNo < columnNo){
                nRow[colNo] = row[colNo];
                ++colNo;
            }
            nRow[columnNo] = values[count];
            ++colNo;
            while(colNo < row.length){
                nRow[colNo] = row[colNo];
                ++colNo;
            }
            data.set(count, nRow);
            ++count;
        }

    }

    @Override
    public void combineColumns(Integer endColNo, Integer[] columnNos){
        System.out.println("\n");
        for(String[] row : data){
            String value;
            if(row[endColNo]!=null){
                value = row[endColNo];
            } else {
                value = "";
            }
            for(Integer i : columnNos){
                if(i < row.length && row[i] != null && i != endColNo) {
                    value = value + " " + row[i];
                    row[i] = null;
                } else if( i>= row.length){
                    break;
                }
            }
            row[endColNo] = value;
        }
    }

    public void reformatDateTime(Integer dtColumn, Integer nDateColumn){
        Long date;
        Integer count = 0;
        String[] dates = new String[data.size()];
        for(String[] content : data){
            if(!content[dtColumn].equals("date")) {
                try {
                    date = new SimpleDateFormat("MM/dd/yy").parse(content[dtColumn]).getTime();
                } catch (Exception e) {
                    System.out.println(content[dtColumn]);
                    date = null;
                    e.printStackTrace();
                }
                dates[count] = date.toString();
            } else {
                dates[count] = "dates(ms)";
            }
            ++count;

        }
        this.addColumn(nDateColumn, dates);
    }


    @Override
    public void dump(){
        Integer count = 0;
        String rline = "";
        for(String[] row : data){
            if( count == 0){
                rline = rline;
            } else{
                rline = rline;
            }
            dumpRow(row, rline);

            rline = " ";
            ++count;
        }
    }

    public static void dumpRow(String [] row, String message){
        for(String element : row){
            message = message + " " + element;
        }
        System.out.println(message);
    }

}
