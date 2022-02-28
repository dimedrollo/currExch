package ru.dopegeek.currex.response;

import java.io.Serializable;

public class ResponseModelValute implements Serializable {

    public ResponseModelCurrency AUD;
    public ResponseModelCurrency AZN;
    public ResponseModelCurrency GBR;
    public ResponseModelCurrency AMD;
    public ResponseModelCurrency BYN;
    public ResponseModelCurrency BGN;
    public ResponseModelCurrency BRL;
    public ResponseModelCurrency HUF;
    public ResponseModelCurrency HKD;
    public ResponseModelCurrency DKK;
    public ResponseModelCurrency USD;
    public ResponseModelCurrency EUR;
    public ResponseModelCurrency INR;
    public ResponseModelCurrency KZT;
    public ResponseModelCurrency CAD;
    public ResponseModelCurrency KGS;
    public ResponseModelCurrency CNY;
    public ResponseModelCurrency MDL;
    public ResponseModelCurrency NOK;
    public ResponseModelCurrency PLN;
    public ResponseModelCurrency RON;
    public ResponseModelCurrency XDR;
    public ResponseModelCurrency SGD;
    public ResponseModelCurrency TJS;
    public ResponseModelCurrency TRY;
    public ResponseModelCurrency TMT;
    public ResponseModelCurrency UZS;
    public ResponseModelCurrency UAH;
    public ResponseModelCurrency CZK;
    public ResponseModelCurrency SEK;
    public ResponseModelCurrency CHF;
    public ResponseModelCurrency ZAR;
    public ResponseModelCurrency KRW;
    public ResponseModelCurrency JPY;

    public ResponseModelCurrency[] getCurrenciesModels() {
        ResponseModelCurrency[] currenciesModels = {AUD, AZN, GBR, AMD, BYN, BGN, BRL, HUF, HKD, DKK,
                USD, EUR, INR, KZT, CAD, KGS, CNY, MDL, NOK, PLN, RON,
                XDR, SGD, TJS, TRY, TMT, UZS, UAH, CZK, SEK, CHF, ZAR, KRW, JPY};
        return currenciesModels;
    }
}
