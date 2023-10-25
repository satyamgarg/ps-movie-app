# PS Movie App
Movie app using the The movie database API’s to show popular movies
currently. The app should display posters of top 10 movies. When user selects a movie from
the list, the app is showing the details of the selected movie. 

### Architecture :
Architecture	        MVI Clean
This project follows MVI+Clean Architecture pattern which include below modules:
- Presentation(app)
- Domain(domain)
- Data(data)

### Data Flow between layers :
1. UI calls method of ViewModel using Channel.
2. ViewModel calls Use case using coroutine.
3. Use case combines fetch data from Repositories.
4. Repository returns data from a Data Source as a Remote.
5. Data return back to the UI.

### Libraries Used :
* [Kotlin][0] : Programming language.
* [Coroutines][1] : For Asynchronous or non-blocking operations.
* [Flows][2] : Data Streaming API which is built on top of Coroutines.
* [Jetpack Compose][3] : Toolkit for building native UI in a declarative way.
* [Retrofit][4] : Type-safe REST client to consume REST web services.
* [Hilt][5] : Dependency injection library.
* [Glide][6] : Image Loading
* [Test Cases][7] : Junit, Mockito

### Screenshots
1. Movie List
![Screenshot_20231025_145842](https://github.com/satyamgarg/ps-movie-app/assets/3114309/b261deaf-44b8-4071-badf-e7ff00d45f56)
2. Movie Details
![Screenshot_20231025_145817](https://github.com/satyamgarg/ps-movie-app/assets/3114309/8c1c50fc-9ae3-4ae7-a02b-a22a348380eb)
<p align="right">(<a href="#readme-top">back to top</a>)</p>
