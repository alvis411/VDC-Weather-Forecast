# VDC-Weather-Forecast

Build Environment:
- Android Studio 4.1.1
- Build tools: 29
- Target SDK: 29

Summary:
This app is written in Kotlin with MVVM pattern and follow Android Architecture Component including:
- ViewModel
- LiveData & Flow
- View Binding
- Room
- Hilt
- Coroutines

Done:
- Query weather from Weather API and render on UI
- Ability to change setting including temperature unit and days of forecast
- Handling exception and show meaningful explaination on UI
- Caching mechanism based on HTTP Caching

Things could be improved:
- Add more caching mechanism on local database on both memory and file.
- Paging for list weather but WeatherAPI just return maximum of 17 records per city so this is not neccessary.
- Add custom style and themes so that we can changed app UI quickly.
- Accessibility support
