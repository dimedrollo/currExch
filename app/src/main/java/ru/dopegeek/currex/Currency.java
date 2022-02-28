package ru.dopegeek.currex;


import ru.dopegeek.currex.response.ResponseModelCurrency;

public class Currency {

    private String id;
    private String charCode;
    private int nominal;
    private String name;
    private float value;

    public Currency() {
    }

    public Currency(String charCode, int nominal, String name, float value) {
        this.charCode = charCode;
        this.name = name;
        this.nominal = nominal;
        this.value = value;
    }

    public Currency(ResponseModelCurrency cur) {
        this.charCode = cur.getCharCode();
        this.name = cur.getName();
        this.nominal = Integer.parseInt(cur.getNominal());
        this.value = Float.parseFloat(cur.getValue());
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
