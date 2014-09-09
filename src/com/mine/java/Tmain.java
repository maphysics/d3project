package com.mine.java;

/**
 * Created by missy on 9/8/2014.
 */
public class Tmain {
    public static void main(String[] args){
        TransformCSV transform = new TransformCSV("C:\\wamp\\www\\d3project\\src\\com\\mine\\resources\\exercisedata2.csv",
                "C:\\wamp\\www\\d3project\\src\\com\\mine\\resources\\exercisedataFormated.csv");
        try {
            transform.load();
        } catch (Exception e){
            System.out.print("unable to load file "+ transform.getSourceFile());
            e.printStackTrace();
        }
        transform.reformatDateTime(0, 1);
        System.out.println("\n");
        transform.dump();
        Integer[] cols = {3,4,5,6,7,8,9};
        transform.combineColumns(3, cols);
        transform.dump();
        try {
            transform.save();
        } catch (Exception e){
            System.out.println("unable to save file "+transform.getSinkFile());
            e.printStackTrace();
        }
    }
}
