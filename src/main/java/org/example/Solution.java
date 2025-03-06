package org.example;

import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        String a = "borrgo";
        String b = "abeja";

        int len_add = lengthAdd(a,b);
        System.out.println(len_add);
        String lexico_res = lexicoGreater(a,b);
        System.out.println(lexico_res);
        String capi_let = capitFirstLetter(a,b);
        System.out.println(capi_let);
    }

    public static int lengthAdd(String a, String b) {
        return a.length() +b.length();
    }

    // condición ? valor_si_true : valor_si_false;
    public static String lexicoGreater(String a,String b) {
        if(a.charAt(0) > b.charAt(0)) {
            return "Yes";
        } else {
            return "No";
        }
    }
    public static String capitFirstLetter(String a, String b) {
        return a.substring(0, 1).toUpperCase() + a.substring(1)+ " " + b.substring(0, 1).toUpperCase() + b.substring(1);
    }
}

