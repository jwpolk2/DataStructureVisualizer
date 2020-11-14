package com.example.datastructurevisualizer;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {
    String fileName = "name";
    String type = "Binary Search Tree";
    String date = "11-1-2020";
    ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
    ArrayList<Integer> numbers2 = new ArrayList<>(Arrays.asList(4, 5, 6));

    //try out different constructors
    File file1 = new File(fileName, type, date);
    File file2 = new File(numbers2);
    File file3 = new File();

    @Test
    void setValues() {
        file1.setValues(numbers);
        assertEquals(numbers, file1.getValues());
    }

    @Test
    void getValues() {
        ArrayList<Integer> valEmpty = new ArrayList<>();
        assertEquals(valEmpty, file1.getValues());
    }

    @Test
    void getValues2() {
        assertEquals(numbers2, file2.getValues());
    }

    @Test
    void getFileName() {
        assertEquals(fileName, file1.getFileName());
    }

    @Test
    void getStructureType() {
        assertEquals(type, file1.getStructureType());
    }

    @Test
    void getDateModified() {
        assertEquals(date, file1.getDateModified());
    }


    @Test
    void setFileName() {
        file1.setFileName("testName");
        assertEquals("testName", file1.getFileName());
    }

    @Test
    void setStructureType() {
        file1.setStructureType("hashMap");
        assertEquals("hashMap", file1.getStructureType());
    }

    @Test
    void setDate() {
        file1.setDate("7-7-2020");
        assertEquals("7-7-2020", file1.getDateModified());
    }
}