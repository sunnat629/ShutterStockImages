# ShutterStockImages
This project is using [Shutterstock API](http://api.shutterstock.com/) and showing in a infinite scroll view using RecycleView.


## Get API Keys & Secrets
Using two types of Keys & Secrets. One is for Co-Routines and another is for RxJava

## Technical choices
- **Android JetPack:** Android Jetpack is a set of components, tools and guidance to make great Android apps. For more details, visit [HERE](https://developer.android.com/jetpack)
- **AndroidX:** It removes all the dependencies of the Support Library means now there will be no impact or no need to change the versions according to the SDK versions. For more details, visit [HERE](https://developer.android.com/jetpack/androidx)
- **Language:** This app is developed on 100% Kotlin (v1.3.51) which is one of the strongest recommenced by JetPack to use Kotlin. For more details, visit [HERE](https://developer.android.com/kotlin/ktx.html)
- **MvvM:** The architecture of this app is view model. This is the latest and recommended architecture for android. MVVM uses data binding and is therefore a more event driven architecture. It can map many views to one view model. Like many fragments or activities can use one viewmodel and also the same values without destroying/loosing them. In MVVM, the view model has no reference to the view. Testing becomes easy after using it. For more details, visit [HERE](https://developer.android.com/topic/libraries/architecture/viewmodel).
- **LiveData:** LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. This awareness ensures LiveData only updates app component observers that are in an active lifecycle state. For more details, visit [HERE](https://developer.android.com/topic/libraries/architecture/livedata).
- **Paging:** The Paging Library helps you load and display small chunks of data at a time. Loading partial data on demand reduces usage of network bandwidth and system resources. For more details, visit [HERE](https://developer.android.com/topic/libraries/architecture/paging/).
- **Dagger2:** For dependency Injection
- **RxJava+RxAndroid:** To compose asynchronous and event-based programs by using observable sequence.
- **Co-Routines:** This is a concurrency design pattern that we can use on Android to simplify code that executes asynchronously. 
- **Picasso:** To handle the Image loading
- **Retrofit:** A type-safe HTTP client. It turns the HTTP API into a Java/Kotlin interface. 
- **OkHttp:** An HTTP & HTTP/2 client for Android 
- **Timber:** To show the logging in debug mode
- **Material Design:** To smooth the UI with material style

## Trade-offs
- Implementing local database using Room
- Adding more parameter of the `/search` endpoint api
- Adding new api endpoints
- Implementing new UI like `SwipeRefreshLayout`, `FabButton`, Some new activity/fragment for more details
- Finding and Fixing bugs
- `Koin` for dependency injection
- Testing

#### References -
- [Async_with__Kotlin_Coroutines](https://speakerdeck.com/0xalihn/async-with-kotlin-coroutines)
- [7 steps to implement Paging library in Android](https://proandroiddev.com/8-steps-to-implement-paging-library-in-android-d02500f7fffe)
- [RXAndroid + Retrofit](https://medium.com/mindorks/rxandroid-retrofit-2fff4f89fa85)
- [Json reader](https://jsoneditoronline.org/)
- [Json to Pojo](http://www.jsonschema2pojo.org/)
