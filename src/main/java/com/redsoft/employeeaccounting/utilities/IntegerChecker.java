package com.redsoft.employeeaccounting.utilities;

public class IntegerChecker {
    public static boolean isInteger(String string) {
        try {
            int i = Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
