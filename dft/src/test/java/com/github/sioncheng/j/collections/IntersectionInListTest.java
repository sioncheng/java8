package com.github.sioncheng.j.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntersectionInListTest {

    @Test
    public void testIntersectionBothEmpty() {
        List<Integer> a1 = new ArrayList<>();
        List<Integer> a2 = new ArrayList<>();
        List<Integer> result = IntersectionInList.intersection(a1, a2);
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }


    @Test
    public void testIntersectionOneEmpty() {
        List<Integer> a1 = Arrays.asList(1,2);
        List<Integer> a2 = new ArrayList<>();
        List<Integer> result = IntersectionInList.intersection(a1, a2);
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());

        result = IntersectionInList.intersection(a2, a1);
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void testIntersectionWithOne() {
        List<Integer> a1 = Arrays.asList(1,2,8,10,20);
        List<Integer> a2 = Arrays.asList(1,3,9,100);

        List<Integer> result = IntersectionInList.intersection(a1, a2);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());


        a1 = Arrays.asList(2,5,8,10,20);
        a2 = Arrays.asList(3,5,9,100);

        result = IntersectionInList.intersection(a1, a2);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());

        a1 = Arrays.asList(2,5,8,10,20);
        a2 = Arrays.asList(20,100);

        result = IntersectionInList.intersection(a1, a2);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testNoIntersection() {
        List<Integer> a1 = Arrays.asList(1,2,8,10,20);
        List<Integer> a2 = Arrays.asList(30,40,50,100);

        List<Integer> result = IntersectionInList.intersection(a1, a2);
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void testIntersectionWithMoreThanOne() {
        List<Integer> a1 = Arrays.asList(1,2,5,8,9,10,20,100);
        List<Integer> a2 = Arrays.asList(1,2,3,5,9,100);

        List<Integer> result = IntersectionInList.intersection(a1, a2);
        Assert.assertNotNull(result);
        Assert.assertEquals(5, result.size());

        result = IntersectionInList.intersection(a2, a1);
        Assert.assertNotNull(result);
        Assert.assertEquals(5, result.size());
    }
}
