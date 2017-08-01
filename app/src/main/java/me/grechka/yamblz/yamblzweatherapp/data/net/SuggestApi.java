package me.grechka.yamblz.yamblzweatherapp.data.net;

import io.reactivex.Single;
import me.grechka.yamblz.yamblzweatherapp.models.response.CityResponseModel;
import me.grechka.yamblz.yamblzweatherapp.models.response.SuggestionResponseModel;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alexander on 22/07/2017.
 */

public interface SuggestApi {

    String API_TYPES = "geocode";
    String API_KEY = "AIzaSyB93d9Y2I-UWEFEQGLdWUfHCjF7cFmlUm8";

    @GET("autocomplete/json")
    Single<SuggestionResponseModel> obtainSuggestedCities(@Query("input") String input,
                                                          @Query("types") String types,
                                                          @Query("key") String apiKey);

    @GET("details/json")
    Single<CityResponseModel> obtainCity(@Query("placeid") String placeId,
                                         @Query("key") String apiKey);
}
