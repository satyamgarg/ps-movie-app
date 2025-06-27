# PS Movie App
Movie app using the The movie database API’s to show popular movies currently. The app display posters of top 10 movies. When user selects a movie from
the list, the app is showing the details of the selected movie. 

### Architecture :
Architecture	        MVI Clean
This project follows MVI+Clean Architecture pattern which include below modules:
- Presentation
- Domain
- Data

### Data Flow between layers :
1. UI calls sends UI events to ViewModel.
2. ViewModel calls Use case using coroutine.
3. Use case fetch data from Repositories.
4. Repository returns data from a Data Source as a Remote.
5. Data return back to the UI.

### Libraries Used :
* [Kotlin] : Programming language.
* [Coroutines] : For Asynchronous or non-blocking operations.
* [Flows] : Data Streaming API which is built on top of Coroutines.
* [Jetpack Compose] : Toolkit for building native UI in a declarative way.
* [Retrofit] : Type-safe REST client to consume REST web services.
* [Hilt] : Dependency injection library.
* [Glide] : Image Loading
* [Test Cases] : Junit, Mockito

### Screenshots
1. Movie List
![Screenshot_20231025_145842](https://github.com/satyamgarg/ps-movie-app/assets/3114309/b261deaf-44b8-4071-badf-e7ff00d45f56)
