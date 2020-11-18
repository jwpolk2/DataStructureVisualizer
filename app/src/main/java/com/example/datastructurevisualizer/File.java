package com.example.datastructurevisualizer;

import java.util.ArrayList;

public class File {
    private String fileName;
    private String structureType; // can be an int if easier
    private String dateModified;
    private ArrayList<Integer> values;
    private boolean isDefault;

    public File() {
        values = new ArrayList<>();
        isDefault = false;
    }

    public File(ArrayList<Integer> values) {
        this.values = values;
        isDefault = false;
    }

    public File(String name, String type, String date) {
        isDefault = false;
        values = new ArrayList<Integer>();
        fileName = name;
        structureType = type;
        dateModified = date;
    }

    public File(String name, String type, String date, ArrayList<Integer> values, boolean isDefault) {
        this.fileName = name;
        this.structureType = type;
        this.dateModified = date;
        this.values = values;
        this.isDefault = isDefault;
    }


    public ArrayList<Integer> getValues() {
        return values;
    }

    public String getFileName() {
        return fileName;
    }

    public String getStructureType() {
        return structureType;
    }

    public String getDateModified() {
        return dateModified;
    }


    public void setValues(ArrayList<Integer> values) {
        this.values = values;
    }

    public void setFileName(String name) {
        fileName = name;
    }

    public void setStructureType(String type) {
        structureType = type;
    }

    public void setDate(String date) {
        dateModified = date;
    }
}
