package com.openstat;

import static org.testng.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.testng.annotations.Test;

@Test
public class RadixTreeTests {
    @Test
    public void testCidrInclusion() {
        IPv4RadixIntTree tr = new IPv4RadixIntTree();
        tr.put(0x0a000000, 0xffffff00, 42);
        tr.put(0x0a000000, 0xff000000, 69);

        assertEquals(tr.selectValue(0x0a202020), 69);
        assertEquals(tr.selectValue(0x0a000020), 42);
        assertEquals(tr.selectValue(0x0b010203), IPv4RadixIntTree.NO_VALUE);
    }

    @Test
    public void testRealistic() throws IOException {
        IPv4RadixIntTree tr = IPv4RadixIntTree.loadFromLocalFile("test/ip-prefix-base.txt");
        BufferedReader br = new BufferedReader(new FileReader("test/test-1.txt"));
        String l;
        int n = 0;
        while ((l = br.readLine()) != null) {
            String[] c = l.split("\t", -1);
            assertEquals(tr.selectValue(c[0]), Integer.parseInt(c[1]), "Mismatch in line #" + n);
            n++;
        }
    }
}