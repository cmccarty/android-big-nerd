Learning Android
================

Hi, I'm Chris. I work at TripAdvisor on our mobile apps, and up to this point have been focusing on iOS as the iOS technical manager. Now I'm trying to learn android development, and am (slowly, where time permits) working my through [Android Programming: The Big Nerd Ranch Guide] (http://www.amazon.com/Android-Programming-Ranch-Guide-Guides/dp/0321804333), and I'm tracking my progress here.

## Why?
Why am I putting this on github? A few reasons

1. Maybe others can benefit from my learnings. My goal is to be overly-thorough in my usage of commits and tags to allow people to jump in, while also adding key learnings that are quickly digestible (not needing to read the whole book)
2. My making this public, I'm creating a social contract and hope it will discourage me from procrastinating
3. It gives me an excuse to practice my markdown...

## Format
I'll tag my progress after each chapter, and maybe even add a commit for subsections. I'll also add other notes to this readme about other important things learned (or keep a short list of subtleties I miss or where things different from iOS development.)

I started late, so you won't see any commits / tags for the first few chapters.

* * * 

# Notes
(I may move this to a "notes" directory with more structure. But I'll throw it all here to avoid pre-optimizations).

## Chapter 1: Your First Android Application
- An *activity* is an instance of Activity, a class in the Android SDK. An activity is responsible for managing user interaction with a screen of information.
- A *layout* defines a set of user interface objects and their position on the screen. A layout is made up of definitions written in XML. Each definition is used to create an object that appears on screen, like a button or some text.
- *Widgets* are the building blocks you use to compose a user interface. A widget can show text or graphics, interact with the user, or arrange other widgets on the screen.
- A *toast* is a short message that informs the user of something but does not require any input or action.
    - `public static Toast makeText(Context context, int resId, int duration)`

### Resources
- Placing strings into a separate file and then referencing them is better because it makes localization easy.
- This method inflates a layout and puts it on screen: `public void setContentView(int layoutResID) `
    - When a layout is inflated, each widget in the layout file is instantiated as defined by its attributes. You specify which layout to inflate by passing in the layout’s resource ID. 
- To access a resource in code, you use its resource ID. The resource ID for your layout is `R.layout.activity_quiz`
- To generate a resource ID for a widget, you include an `android:id` attribute in the widget’s definition.
- Notice that there is a + sign in the values for `android:id` but not in the values for `android:text`. This is because you are creating the IDs and only referencing the strings.

### Code Style / Practices
- Notice the m prefix on the two member (instance) variable names. This prefix is an Android naming convention that we will follow throughout this book.
- All of the listeners in this book will be implemented as anonymous inner classes. Doing so puts the implementations of the listeners’ methods right where you want to see them. And there is no need for the overhead of a named class because the class will be used in one place only.

### Building
- During the build process, the Android tools take your resources, code, and the `AndroidManifest.xml` file (which contains meta-data about the application) and turn them into an .apk file. This file is then signed with a debug key, which allows it to run on the emulator. (To distribute your .apk to the masses, you have to sign it with a release key.
- As part of the build process, aapt (Android Asset Packaging Tool) compiles layout file resources into a more compact format. These compiled resources are packaged into the .apk file. Then, when `setContentView(…)` is called in the QuizActivity’s `onCreate(…)` method, the `QuizActivity` uses the `LayoutInflater` class to instantiate each of the View objects as defined in the layout file.
- To use Gradle from the command line, navigate to your project’s directory and run the following command: `$ ./gradlew tasks`. This will show you a list of available tasks you can execute. The one you want is called “installDebug”.


## Chapter 2: Android and the Model-View-Controller
### Android Studio
- Tip: auto-generate getters and settings
    - The first step is to configure Android Studio to recognize the m prefix for member variables. Open Android Studio’s preferences (from the Android Studio menu on Mac and from File → Settings on Windows). Expand Editor and then expand Code Style. Select Java, then choose the Code Generation tab. In the Naming table, select the Field row and add m as the Name prefix for fields. Then add s as the Name prefix for static fields.
- In Android, a controller is typically a subclass of `Activity`, `Fragment`, or `Service`.

### Configuring your device for development
- On devices running Android 4.2 or later, Developer options is not visible by default. To enable it, go to Settings → About Tablet/ Phone and press Build Number 7 times. Then you can return to Settings, see Developer options, and enable USB debugging.

### Resources for different screen sizes
- under `/res` you will find different directories for different screen sizes. Android will pick the best file to use based on the device. Make sure that the names are the same so that you can use the same `resource id` in code.
    - eg: `drawable-hdpi`, `drawable-mdpi`, `drawable-xhdpi`, `drawable-xxhdpi` 


## Chapter 3: The Activity Lifecycle
- `onCreate()`, `onStart()`, `onResume()`, `onPause()`, `onStop()`, `onDestroy()`
- a stopped activity’s survival is not guaranteed. When the system needs to reclaim memory, it will destroy stopped activities.
- Even if the window only partially covers the activity, the activity is paused and cannot be interacted with. The activity resumes when the pop-up window is dismissed.
- Android will never destroy a running activity to reclaim memory – the activity must be in the paused or stopped state to be destroyed. If an activity is paused or stopped, then its `onSaveInstanceState(…)` method has been called. 
- When `onSaveInstanceState(…)` is called, the data is saved to the Bundle object. That Bundle object is then stuffed into your activity’s activity record by the OS.
- When your activity is stashed, an Activity object does not exist, but the activity record object lives on in the OS. The OS can reanimate the activity using the activity record when it needs to.
- Under some situations, Android will not only kill your activity but also completely shut down your application’s process. This will only happen if the user is not currently looking at your application, but it can (and does) happen. Even in this case, the activity record will live on and enable a quick restart of your activity if the user returns.
- When the user presses the Back button, your activity really gets destroyed, once and for all. At that point, your activity record is discarded. Activity records are also typically discarded on reboot and may also be discarded if they are not used for a long time.



### Logging
`android.util.Log` class sends log messages to a shared system-level log.

| Log Level | Method |
| --------- | ------ | 
| ERROR | Log.e(...) | 
| WARNING | Log.w(...) | 
| INFO | Log.i(...) | 
| DEBUG | Log.d(...) | 
| VERBOSE | Log.v(...) | 


### Device Configurations
- Rotating the device changes the `device configuration`. The device configuration is a set of characteristics that describe the current state of an individual device. The characteristics that make up the configuration include screen orientation, screen density, screen size, keyboard type, dock mode, language, and more.
- When the device is in landscape orientation, Android will find and use resources in the `res/layout-land` directory. Otherwise, it will stick with the default in `res/layout/`.
- `FrameLayout` is the simplest `ViewGroup` and does not arrange its children in any particular manner.
- You can override `onSaveInstanceState(…)` to save additional data to the bundle and then read that data back in `onCreate(…)`.

### Bundle
Note that the types that you can save to and restore from a `Bundle` are primitive types and classes that implement the `Serializable` or `Parcelable` interfaces. It is usually a bad practice to put objects of custom types into a Bundle, however, because the data might be stale when you get it back out. It is a better choice to use some other kind of storage for the data and put a primitive identifier into the Bundle instead.

### Testing onSaveInstanceState
Launch Settings and select Developer options, turn on the setting labeled Don’t keep activities. Now run your app and press the Home button. Pressing Home causes the activity to be paused and stopped. Then the stopped activity will be destroyed just as if the Android OS had reclaimed it for its memory. Then you can restore the app to see if your state was saved as you expected.


* * * 

## Glossary
*(running list)*

| Term | Info |
| ---- | ---- |
| **activity** | An activity is an instance of Activity, a class in the Android SDK. An activity is responsible for managing user interaction with a screen of information. |
| **layout** | A layout defines a set of user interface objects and their position on the screen. A layout is made up of definitions written in XML. Each definition is used to create an object that appears on screen, like a button or some text. |
| **toast** | A *toast* is a short message that informs the user of something but does not require any input or action. |
| **widget** | Widgets are the building blocks you use to compose a user interface. A widget can show text or graphics, interact with the user, or arrange other widgets on the screen. |
| **FrameLayout** | FrameLayout is the simplest ViewGroup and does not arrange its children in any particular manner. |
