package com.quypham.vdc

object Constants {
    //internal http error code
    const val INTERNAL_HTTP_UNKNOWN_ERROR = 600
    const val INTERNAL_HTTP_NO_INTERNET_CONNECTION = 601
    const val INTERNAL_HTTP_CLIENT_TIMEOUT = 408
    const val UNSATISFIABLE_REQUEST= 504
    const val INTERNAL_HTTP_NO_RESULT = 602

    //weather api base url
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val QUERY_APP_ID = "60c6fbeb4b93ac653c492ba806fc346d"

    //database constant
    const val APP_DATABASE_VERSION = 1
    const val APP_DATABASE_NAME = "app.db"

    const val MINIMUM_DAYS_FORECAST = 1
    //tested on  weather api,  this could be larger but  user will receive error  anyway
    const val MAXIMUM_DAYS_FORECAST = 17
    const val DEFAULT_DAYS_FORECAST = 5
    const val MINIMUM_CITY_CHARACTERS_ALLOW = 3

    const val LOCAL = "local"
    const val REMOTE = "remote"
}