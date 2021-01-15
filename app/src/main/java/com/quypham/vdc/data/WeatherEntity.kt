package com.quypham.vdc.data

class WeatherForecastEntity constructor(var city: WeatherCity,
                                        var cod: String,
                                        var message: Double,
                                        var cnt: Int,
                                        var list: List<WeatherDetail>)

class WeatherCity constructor(var id: Int,
                              var name: String,
                              var coord: Coordinate,
                              var country: String,
                              var population: Int,
                              var timezone: Int)

class Coordinate constructor(var lat: Double,
                             var lng: Double)

class WeatherDetail constructor(var dt: Long,
                                var sunrise: Long,
                                var sunset: Long,
                                var temp: WeatherTemp,
                                var feels_like: WeatherFeelLike,
                                var pressure: Int,
                                var humidity: Int,
                                var weather: List<WeatherDescription>,
                                var speed: Double,
                                var deg: Int,
                                var clouds: Int,
                                var pop: Double)

class WeatherTemp constructor(var day: Double,
                              var min: Double,
                              var max: Double,
                              var night: Double,
                              var eve: Double,
                              var mom: Double)

class WeatherFeelLike constructor(var day: Double,
                                  var night: Double,
                                  var eve: Double,
                                  var mom: Double)

class WeatherDescription constructor(var id: Int, var main: String, var description: String, var icon: String)