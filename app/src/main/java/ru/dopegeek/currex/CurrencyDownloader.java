package ru.dopegeek.currex;


import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

import ru.dopegeek.currex.response.OptionalResponse;


public class CurrencyDownloader {

    private static final String CURRENCY_SOURCE = "https://www.cbr-xml-daily.ru/daily_json.js";
    private static final int TIMEOUT = 100000;
    private static final String USER_AGENT = "currencyExchange";


    public static OptionalResponse getCurrency() {
        try {
            Connection.Response response = Jsoup.connect(CURRENCY_SOURCE)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .method(Connection.Method.GET)
                    .timeout(TIMEOUT)
                    .userAgent(USER_AGENT)
                    .execute();
            return OptionalResponse.of(response);
        } catch (IOException e) {
            throw new RuntimeException("I/O exception was catched while try get currencies!", e);
        }
    }


}


