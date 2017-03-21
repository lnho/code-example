package com.lnho.example.java.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LINHAO on 2017-3-4.
 */
public class HeapOOM {
    static class OOMObject{

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while(true){
            list.add(new OOMObject());
        }
    }
}
