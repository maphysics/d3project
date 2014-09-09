package com.maphysics.java;

/**
 * Created by missy on 9/8/2014.
 */
public interface ITransform {

    public void load() throws Exception;
    public void save() throws Exception;
    public void addColumn(Integer columnNo, String[] values);
    public void combineColumns(Integer endColNo, Integer[] columnNos);
    public void dump();

}
