package com.andy.kotlin.gank;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Andy on 2017/7/8.
 */

public class ExampleUnitJavaTest {

    @Test
    public void test(){
        String str ="[\"1002201504303120\",\"1002201504253100\",\"1002201504213080\"]";
        List<String> list = Arrays.asList(str);
        System.err.println(list);

    }
}
