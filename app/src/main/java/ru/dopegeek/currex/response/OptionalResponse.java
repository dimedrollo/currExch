package ru.dopegeek.currex.response;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jsoup.Connection.Response;

import java.util.NoSuchElementException;


/**
 * Represents optional ru.dopegeek.currencyexchange.response which may be presented or not.
 *
 * @see OptionalResponse#get()
 * @see OptionalResponse#isPresent()
 */
public class OptionalResponse {

    private static final Gson GSON = new GsonBuilder().create();

    private final ResponseModel response;
    private final String rawResponse;

    private final int statusCode;
    private final String statusMessage;


    public OptionalResponse(ResponseModel response, String rawResponse, int statusCode, String statusMessage) {
        this.response = response;
        this.rawResponse = rawResponse;

        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    /**
     * Creates optional ru.dopegeek.currencyexchange.response instance from a Jsoup connection ru.dopegeek.currencyexchange.response.
     *
     * @param jsoupResponse Jsoup connection ru.dopegeek.currencyexchange.response.
     * @return A new {@link OptionalResponse} instance from received Jsoup ru.dopegeek.currencyexchange.response.
     */
    public static OptionalResponse of(Response jsoupResponse) {
        int status = jsoupResponse.statusCode();
        String statusMessage = jsoupResponse.statusMessage();

        String body = jsoupResponse.body();

        if (status != 200)
            return new OptionalResponse(null, body, status, statusMessage);

        ResponseModel model = GSON.fromJson(body, ResponseModel.class);

        return new OptionalResponse(model, body, status, statusMessage);
    }

    /**
     * If a value is present in this {@code OptionalResponse}, returns the value,
     * otherwise throws {@code NoSuchElementException}.
     *
     * @return the non-null value held by this {@code OptionalResponse}
     * @throws NoSuchElementException if there is no value present
     * @see OptionalResponse#isPresent()
     */
    public ResponseModel get() {
        if (response == null)
            throw new NoSuchElementException("No value present");

        return response;
    }

    /**
     * Checks if the current instance presents a valid ru.dopegeek.currencyexchange.response model or not.
     *
     * @return 'true' if it's presents or 'false' if not.
     */
    public boolean isPresent() {
        return response != null;
    }

    /**
     * Returns a raw ru.dopegeek.currencyexchange.response as json string from the primary Jsoup ru.dopegeek.currencyexchange.response.
     *
     * @return Raw ru.dopegeek.currencyexchange.response as json string.
     */
    public String getRaw() {
        return rawResponse;
    }

    /**
     * Returns a ru.dopegeek.currencyexchange.response status code.
     * <p>
     * It must be 200 as OK status code.
     *
     * @return Response status code.
     */
    public int statusCode() {
        return statusCode;
    }

    /**
     * Returns a ru.dopegeek.currencyexchange.response status message.
     * <p>
     * It must be OK as successfull request status.
     *
     * @return Response status message.
     */
    public String statusMessage() {
        return statusMessage;
    }

    @Override
    public String toString() {
        return "OptionalResponse{"
                + "present=" + isPresent() + ", "
                + "response=" + response + ", "
                + "rawResponse=" + rawResponse + ", "
                + "statusCode=" + statusCode
                + ", statusMessage=" + statusMessage + "}";
    }

}
