package ru.dopegeek.currex.database;

public class DbSchema {

    public static final class CurrencyTable {
        public static final String NAME = "currency";

        public static final class Cols {
            public static final String CHARCODE = "charcode";
            public static final String NOMINAL = "nominal";
            public static final String NAME = "name";
            public static final String VALUE = "value";

        }
    }
}
