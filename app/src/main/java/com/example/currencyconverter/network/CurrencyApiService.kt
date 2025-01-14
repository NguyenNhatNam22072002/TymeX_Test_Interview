import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Interface để gọi các API
interface CurrencyApiService {
    @GET("latest")
    suspend fun getExchangeRates(
        @Query("base") baseCurrency: String,
        @Query("symbols") targetCurrencies: String,
        @Query("access_key") apiKey: String
    ): Response<ExchangeRateResponse>
}

