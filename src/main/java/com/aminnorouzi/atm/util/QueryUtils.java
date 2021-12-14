package com.aminnorouzi.atm.util;

public class QueryUtils {

    private static final String CREATE = "CREATE ";
    private static final String TABLE = "TABLE ";
    private static final String IF = "IF ";
    private static final String NOT = "NOT ";
    private static final String EXISTS = "EXISTS ";
    private static final String INSERT = "INSERT ";
    private static final String INTO = "INTO ";
    private static final String VALUES = "VALUES ";
    private static final String UPDATE = "UPDATE ";
    private static final String SET = "SET ";
    private static final String WHERE = "WHERE ";
    private static final String DELETE = "DELETE ";
    private static final String FROM = "FROM ";
    private static final String SELECT = "SELECT ";

    public static String creatTableQuery(String table, String columns) {
        return CREATE + TABLE + IF + NOT + EXISTS + table + " (" + columns + ")";
    }

    public static String insetQuery(String table, String columns, String values) {
        return INSERT + INTO + table + " (" + columns + ") " + VALUES + "(" + values + ")";
    }

    public static String updateQuery(String table, String columns, String condition) {
        return UPDATE + table + " " + SET + columns + " " + WHERE + condition;
    }

    public static String deleteQuery(String table, String condition) {
        return DELETE + FROM + table + " " + WHERE + condition;
    }

    public static String selectQuery(String columns, String table, String condition) {
        return SELECT + columns + " " + FROM + table + " " + WHERE + condition;
    }

    public static String selectQuery(String columns, String table) {
        return SELECT + columns + " " + FROM + table;
    }
}
