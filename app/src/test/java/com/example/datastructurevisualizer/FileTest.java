package com.example.datastructurevisualizer;

import junit.framework.TestCase;

import java.util.ArrayList;


public class FileTest extends TestCase {

    private String name = "test";
    private String dst = "testDst";
    private String dateMod = "today";
    private ArrayList<Integer> testVals = new ArrayList<>();

    private File file1 = new File();
    private File file3 = new File(name, dst, dateMod);

    public void testSet_and_GetValues() {
        File file2 = new File(testVals);

        testVals.add(1);
        testVals.add(2);
        file2.setValues(testVals);

        ArrayList<Integer> testVals = file2.getValues();
        assertEquals(2, testVals.size());
    }

    public void testGet_and_SetStructureType() {
        file3.setStructureType(dst);

        assertEquals("testDst", file3.getStructureType());
    }

    public void testSet_and_GetDateModified() {
        file3.setDate(dateMod);

        assertEquals("today", file3.getDateModified());
    }

    public void testSet_and_GetFileName() {
        file3.setFileName(name);

        assertEquals("test", file3.getFileName());
    }
}