package ru.dopegeek.currex.response;

public class ResponseModelCurrency {


    public String ID;
    public String NumCode;
    public String CharCode;
    public String Nominal;
    public String Name;
    public String Value;
    public String Previous;

    public String getCharCode() {
        return CharCode;
    }

    public String getNominal() {
        return Nominal;
    }

    public String getName() {
        return Name;
    }

    public String getValue() {
        return Value;
    }


    @Override
    public String toString() {
        return "Currency{"
                + "id=" + ID + ", "
                + "NumCode=" + NumCode + ", "
                + "charCode=" + CharCode + ", "
                + "nominal=" + Nominal + ", "
                + "name=" + Name + ", "
                + "value=" + Value + ", "
                + "previous=" + Previous + "}";
    }
}

