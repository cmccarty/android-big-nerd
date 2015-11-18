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

- [Chapter 1: Your First Android Application](#chapter-1-your-first-android-application)
- [Chapter 2: Android and the Model-View-Controller](#chapter-2-android-and-the-model-view-controller)
- [Chapter 3: The Activity Lifecycle](#chapter-3-the-activity-lifecycle)
- [Chapter 4: Debugging Android Apps](#chapter-4-debugging-android-apps)
- [Chapter 5: Your Second Activity](#chapter-5-your-second-activity)
- [Chapter 6: Android SDK Versions and Compatibility](#chapter-6-android-sdk-versions-and-compatibility)
- [Chapter 7: UI Fragments and the Fragment Manager](#chapter-7-ui-fragments-and-the-fragment-manager)
- [Chapter 8: Creating User Interfaces with Layouts and Widgets](#chapter-8-creating-user-interfaces-with-layouts-and-widgets)
- [Chapter 9: Displaying Lists with RecyclerView](#chapter-9-displaying-lists-with-recyclerview)
- [Glossary](#glossary)


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


## Chapter 4: Debugging Android Apps
If a crash occurs while a device is not plugged in, all is not lost. The device will store the latest lines written to the log. The length and expiration of the stored log depends on the device, but you can usually count on retrieving log results within 10 minutes. Just plug in the device and select your device in the Devices view. LogCat will fill itself with the stored log.

### Logging Stack Traces
`String Context` and `String Message` are required, you can also pass in a `Throwable` (either other exception or a new one to log a stack trace)

`Log.d( TAG, "Updating question text for question #" + mCurrentIndex, new Exception());`

Logging out stack traces has the advantage that you can see stack traces from multiple places in one log. The downside is that to learn something new you have to add new log statements, rebuild, deploy, and navigate through your app to see what happened. The debugger is more convenient. If you run your app with the debugger attached, then you can set a breakpoint while the application is still running and poke around to get information about multiple issues.

### Android Lint
Android Lint is a static analyzer for Android code. A static analyzer is a program that examines your code to find defects without running it. Android Lint uses its knowledge of the Android frameworks to look deeper into your code and find problems that the compiler cannot.

Select Analyze → Inspect Code... from the menu bar. You will be asked which parts of your project you would like to inspect. Choose Whole project. Android Studio will now run Lint as well as a few other static analyzers on your code.


## Chapter 5: Your Second Activity
Creating an activity typically involves touching at least three files: the Java class file, an XML layout, and the application manifest.

Notice the special XML namespace for `tools` and the `tools:text` attribute on the `TextView` widget where the answer will appear. This namespace allows you to override any attribute on a widget for the purpose of displaying it differently in the Android Studio preview. Since TextView has a `text` attribute, you can provide a literal dummy value for it to help you know what it will look like at runtime.

The `manifest` is an XML file containing metadata that describes your application to the Android OS. The file is always named `AndroidManifest.xml`, and it lives in the `app/manifests` directory of your project. Every activity in an application must be declared in the manifest so that the OS can access it.

### Starting an Activity
The simplest way one activity can start another is with the Activity method: `public void startActivity(Intent intent)` 

You might guess that `startActivity(…)` is a static method that you call on the `Activity` subclass that you want to start. But it is not. When an activity calls `startActivity(…)`, this call is sent to the OS. In particular, it is sent to a part of the OS called the `ActivityManager`. The `ActivityManager` then creates the Activity instance and calls its `onCreate(…)` method.

An activity may be started from several different places, so you should define keys for extras on the activities that retrieve and use them. **Using your package name as a qualifier for your extra** prevents name collisions with extras from other apps.

### Communicating with intents
- An `intent` is an object that a component can use to communicate with the OS. The only components you have seen so far are `activities`, but there are also `services`, `broadcast receivers`, and `content providers`. Intents are multi-purpose communication tools, and the Intent class provides different constructors depending on what you are using the intent to do.
    - The `Class` argument specifies the activity class that the ActivityManager should start. 
    - The `Context` argument tells the ActivityManager which application package the activity class can be found in.
- When you create an Intent with a Context and a Class object, you are creating an `explicit` intent. You use explicit intents to start activities within your application.
- When an activity in your application wants to start an activity in another application, you create an `implicit` intent.
- `Extras` are arbitrary data that the calling activity can include with an intent.
    - `public Intent putExtra( String name, boolean value)`
    - It returns the `Intent` itself, so you can chain multiple calls if you need to.
- Using a `newIntent(…)` method like this for your activity subclasses will make it easy for other code to properly configure their launching intents.
- When you want to hear back from the child activity, you call the following Activity method: `public void startActivityForResult( Intent intent, int requestCode)`
    - The `request code` is a user-defined integer that is sent to the child activity and then received back by the parent. It is used when an activity starts more than one type of child activity and needs to know who is reporting back.
    
#### Setting a result
- There are two methods you can call in the child activity to send data back to the parent:   
    - `public final void setResult(int resultCode)` 
    - `public final void setResult(int resultCode, Intent data)`
- Then, when the user presses the Back button, the ActivityManager calls the following method on the parent activity:   
    - `protected void onActivityResult( int requestCode, int resultCode, Intent data)`

### How Android Sees Your Activities
- the `ActivityManager` maintains a `back stack` and that this back stack is not just for your application’s activities. Activities for all applications share the back stack, which is one reason the ActivityManager is involved in starting your activities and lives with the OS and not your application. The stack represents the use of the OS and device as a whole rather than the use of a single application.
- When you click on the GeoQuiz app in the launcher, the OS does not start the application; it starts an activity in the application. More specifically, it starts the application’s `launcher activity`.
    - Launcher activity status is specified in the manifest by the `intent-filter` element in QuizActivity’s declaration
- A call to `Activity.finish()` in CheatActivity would also pop the CheatActivity off the stack.


## Chapter 6: Android SDK Versions and Compatibility
Current Platform usage dashboard: [http://developer.android.com/about/dashboards/index.html](http://developer.android.com/about/dashboards/index.html)

- The `minSdkVersion` value is a hard floor below which the OS should refuse to install the app.
- The `targetSdkVersion` value tells Android which API level your app was designed to run on. Most often this will be the latest Android release.
- The `compileSdkVersion`, or build target, specifies which version to use when building your own code.

Thanks to improvements in Android Lint, potential problems caused by calling newer code on older devices can be caught at compile time. If you use code from a higher version than your minimum SDK, Android Lint will report build errors.

The `Build.VERSION.SDK_INT` constant is the device’s version of Android. You then compare that version with the constant that stands for the Lollipop release. 

Version codes are listed at [http://developer.android.com/reference/android/os/Build.VERSION_CODES.html](http://developer.android.com/reference/android/os/Build.VERSION_CODES.html).


## Chapter 7 UI Fragments and the Fragment Manager
### Introducing Fragments
A `fragment` is a controller object that an activity can deputize to perform tasks. Most commonly, the task is managing a user interface. The user interface can be an entire screen or just one part of the screen. A fragment managing a user interface is known as a `UI fragment`. A UI fragment has a view of its own that is inflated from a layout file. The fragment’s view contains the interesting UI elements that the user wants to see and interact with. The activity’s view contains a spot where the fragment’s view will be inserted. Or it might have several spots for the views of several fragments.

Using UI fragments separates the UI of your app into building blocks; achieving this UI flexibility comes at a cost: more complexity, more moving parts, and more code.

For now, think of hosting as the activity providing a spot in its view hierarchy where the fragment can place its view (Figure   7.5). A fragment is incapable of getting a view on screen itself. Only when it is placed in an activity’s hierarchy will its view appear.

### Support Library
- The support library includes a complete implementation of fragments that work all the way back to API level 4.
- Using fragments requires activities that know how to manage fragments. The `FragmentActivity` class knows how to manage the support version of fragments.
- Maven coordinates format: `groupId`:`artifactId`:`version`

### Hosting a UI Fragment
- To host a UI fragment, an activity must:
    - define a spot in its layout for the fragment’s view 
    - manage the lifecycle of the fragment instance
- Because a fragment works on behalf of an activity, its state should reflect the activity’s state. **One critical difference between the fragment lifecycle and the activity lifecycle** is that fragment lifecycle methods are called by the hosting activity, not the OS. The OS knows nothing about the fragments that an activity is using to manage things. Fragments are the activity’s internal business.
- You have two options when it comes to hosting a UI fragment in an activity: 
    - add the fragment to the activity’s layout (*layout fragment*): It is simple but inflexible. If you add the fragment to the activity’s layout, you hardwire the fragment and its view to the activity’s view and cannot swap out that fragment during the activity’s lifetime.
    - add the fragment in the activity’s code: More complex, but it is the only way to have control at runtime over your fragments. You determine when the fragment is added to the activity and what happens to it after that. You can remove the fragment, replace it with another, and then add the first fragment back again.
- to achieve real UI flexibility you must add your fragment in code.

### Creating a UI Fragment
- The steps to creating a UI fragment are the same as those you followed to create an activity: 
    - compose a user interface by defining widgets in a layout file 
    - create the class and set its view to be the layout that you defined 
    - wire up the widgets inflated from the layout in code
- In `Fragment.onCreate(…)` you do not inflate the fragment’s view. You configure the fragment instance in `Fragment.onCreate(…)`, but you create and configure the fragment’s view in another fragment lifecycle method: `public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`
    - This method is where you inflate the layout for the fragment’s view and return the inflated View to the hosting activity. The `LayoutInflater` and `ViewGroup` parameters are necessary to inflate the layout. The `Bundle` will contain data that this method can use to recreate the view from a saved state.
- Within `onCreateView(…)`, you explicitly inflate the fragment’s view by calling `LayoutInflater.inflate(…)` and passing in the layout resource ID. The second parameter is your view’s parent, which is usually needed to configure the widgets properly. The third parameter tells the layout inflater whether to add the inflated view to the view’s parent. You pass in `false` because you will add the view in the activity’s code.
- Getting references in `Fragment.onCreateView(…)` works nearly the same as in `Activity.onCreate(…)`. The only difference is that you call `View.findViewById( int)` on the fragment’s view. The `Activity.findViewById( int)` method that you used before is a convenience method that calls `View.findViewById( int)` behind the scenes. The Fragment class does not have a corresponding convenience method, so you have to call the real thing.

### Adding a UI Fragment to the FragmentManager
- The FragmentManager is responsible for managing your fragments and adding their views to the activity’s view hierarchy. It handles 2 things: 1) a list of fragments; 2) a back stack of fragment transactions
- To add a fragment to an activity in code, you make explicit calls to the activity’s FragmentManager.
- Fragment transactions are used to add, remove, attach, detach, or replace fragments in the fragment list. They are the heart of how you use fragments to compose and recompose screens at runtime. The FragmentManager maintains a back stack of fragment transactions that you can navigate.
- The FragmentManager.beginTransaction() method creates and returns an instance of FragmentTransaction. The FragmentTransaction class uses a fluent interface - methods that configure FragmentTransaction return a FragmentTransaction instead of void, which allows you to chain them together.
    - `fm.beginTransaction().add(R.id.fragment_container, fragment).commit();`
- A container view ID serves two purposes: 
    - It tells the FragmentManager where in the activity’s view the fragment’s view should appear. 
    - It is used as a unique identifier for a fragment in the FragmentManager’s list.

#### The FragmentManager and the fragment lifecycle
- The `FragmentManager` of an activity is responsible for calling the lifecycle methods of the fragments in its list. The `onAttach(Activity)`, `onCreate(Bundle)`, and `onCreateView(…)` methods are called when you add the fragment to the FragmentManager.
- If you add a fragment while the activity is already running, `FragmentManager` immediately walks the fragment through whatever steps are necessary to get it caught up to the activity’s state.

### Application Architecture with Fragments
- Fragments are intended to encapsulate major components in a reusable way. A major component in this case would be on the level of an entire screen of your application. If you have a significant number of fragments on screen at once, your code will be littered with fragment transactions and unclear responsibility. A better architectural solution for reuse with smaller components is to extract them into a custom view
- **A good rule of thumb is to have no more than two or three fragments on the screen at a time**


## Chapter 8: Creating User Interfaces with Layouts and Widgets
### Styles, themes, and theme attributes
- A `style` is an XML resource that contains attributes that describe how a widget should look and behave. You can create your own styles. You add them to a styles file in `res/values/` and refer to them in layouts like this: `@style/my_own_style`.
- A `theme` is a collection of styles. Structurally, a theme is itself a style resource whose attributes point to other style resources. You can apply a style from the app’s theme to a widget using a `theme attribute reference`.

### Screen pixel densities and dp and sp
Android design guidelines: [http://developer.android.com/design/index.html](http://developer.android.com/design/index.html)

| type | Description |
| ---- | ----------- |
| `dp` | Short for density-independent pixel and usually pronounced “dip.” You typically use this for margins, padding, or anything else for which you would otherwise specify size with a pixel value. When your display is a higher density, density-independent pixels will expand to fill a larger number of screen pixels. One dp is always 1/ 160th of an inch on a device’s screen. You get the same size regardless of screen density. |
| `sp` | Short for scale-independent pixel. Scale-independent pixels are density-independent pixels that also take into account the user’s font size preference. You will almost always use sp to set display text size. |
| `pt`, `mm`, `in` | These are scaled units like dp that allow you to specify interface sizes in points (1/72 of an inch), millimeters, or inches. However, we do not recommend using them: not all devices are correctly configured for these units to scale correctly. |

- Attributes whose names do not begin with `layout_` are directions to the widget. When it is inflated, the widget calls a method to configure itself based on each of these attributes and their values. 
- When an attribute’s name begins with `layout_`, that attribute is a direction to that widget’s parent. These attributes are known as layout parameters, and they tell the parent layout how to arrange the child element within the parent.
- Margins
    - Margin attributes are layout parameters. They determine the distance between widgets. Given that a widget can only know about itself, margins must be the responsibility of the widget’s parent.
- Padding
    - The android:padding attribute tells the widget how much bigger than its contents it should draw itself.

### Layout Weight
- The `android:layout_weight` attribute tells the `LinearLayout` how to distribute its children. You have given both widgets the same value, but that does not necessarily make them the same width on screen. To determine the width of its child views, `LinearLayout` uses a mixture of the `layout_width` and `layout_weight` parameters. 
- `LinearLayout` makes two passes to set the width of a view. 
    - In the first pass, LinearLayout looks at `layout_width` (or `layout_height`, for vertical orientation).
    - In the next pass, LinearLayout allocates any extra space based on the values for `layout_weight`
- What if you want the LinearLayout to allocate exactly 50% of its width to each view? You simply skip the first pass by setting the layout_width of each widget to 0dp instead of wrap_content. This leaves layout_weight the sole component in the LinearLayout’s decision making
- **remember that a widget must have the same `android:id` attribute in every layout in which it appears so that your code can find it.**


## Chapter 9: Displaying Lists with RecyclerView
### Recycler View, Adapter, and ViewHolder
- `RecyclerView` is a subclass of `ViewGroup`. It displays a list of child `View` objects, one for each item in your list of items. Depending on the complexity of what you need to display, these child Views can be complex or very simple.
- Instead of creating 100 Views, it creates 12 – enough to fill the screen. When a view is scrolled off the screen, RecyclerView reuses it rather than throwing it away. In short, it lives up to its name: it recycles views over and over.
- The `RecyclerView`’s only responsibilities are recycling TextViews and positioning them on the screen. To get the `TextViews` in the first place, it works with two classes that you will build in a moment: an `Adapter` subclass and a `ViewHolder` subclass.
- A `RecyclerView` never creates Views by themselves. It always creates `ViewHolders`, which bring their `itemViews` along for the ride

#### Adapters
- `RecyclerView` does not create `ViewHolders` itself. Instead, it asks an `adapter`. An adapter is a controller object that sits between the RecyclerView and the data set that the RecyclerView should display.
- The adapter is responsible for:
    - creating the necessary ViewHolders
    - binding ViewHolders to data from the model layer
- First, the RecyclerView asks how many objects are in the list by calling the adapter’s `getItemCount()` method. 
- Then the RecyclerView calls the adapter’s `createViewHolder(ViewGroup, int)` method to create a new ViewHolder, along with its juicy payload: a `View` to display.
- Finally, the RecyclerView calls `onBindViewHolder(ViewHolder, int)`. The RecyclerView will pass a ViewHolder into this method along with the position. The adapter will look up the model data for that position and bind it to the ViewHolder’s View. To bind it, the adapter fills in the View to reflect the data in the model object.
- Note that `createViewHolder(ViewGroup, int)` will happen a lot less often than `onBindViewHolder(ViewHolder, int)`. Once a sufficient number of ViewHolders have been created, RecyclerView stops calling `createViewHolder(…)`. Instead, it saves time and memory by recycling old ViewHolders.

#### Using Recycler View
- RecyclerView does not do the job of positioning items on the screen itself. It delegates that out to the LayoutManager. The LayoutManager handles the positioning of items and also defines the scrolling behavior.
- You will use the LinearLayoutManager, which will position the items in the list vertically. Later on in this book, you will use GridLayoutManager to arrange items in a grid instead.

#### Implementing an Adapter and ViewHolder
- `onCreateViewHolder` is called by the `RecyclerView` when it needs a new View to display an item. In this method, you create the `View` and wrap it in a `ViewHolder`. The `RecyclerView` does not expect that you will hook it up to any data yet.
- Next, `onBindViewHolder`: This method will bind a `ViewHolder`’s View to your model object. It receives the `ViewHolder` and a position in your data set. To bind your View, you use that position to find the right model data. Then you update the View to reflect that model data.

### Customizing List Items
- In a `RelativeLayout`, you use layout parameters to arrange child views relative to the root layout and to each other.
- In a layout file, an ID must be defined with an `@+id` before other widgets can use that ID in their own definitions with `@id`.

### Using a custom item view
- Calls to findViewById(int) are often expensive, `ViewHolder` can relieve a lot of this pain. By stashing the results of these `findViewById(int)` calls, you only have to spend that time in `createViewHolder(…)`. When `onBindViewHolder(…)` is called, the work is already done. Which is nice, because `onBindViewHolder(…)` is called much more often than `onCreateViewHolder(…)`.

### For the More Curious: ListView and GridView 
- The core Android OS includes `ListView`, `GridView`, and `Adapter` classes.
- Another key feature of `RecyclerView` is the animation of items in the list. Animating the addition or removal of items in a `ListView` or `GridView` is a complex and error-prone task. `RecyclerView` makes this much easier, includes a few built-in animations, and allows for easy customization of these animations.



* * * 

## Glossary
*(running list)*

| Term | Info |
| ---- | ---- |
| **activity** | An activity is an instance of Activity, a class in the Android SDK. An activity is responsible for managing user interaction with a screen of information. |
| **extra** | `Extras` are arbitrary data that the calling activity can include with an intent |
| **fragment** | A `fragment` is a controller object that an activity can deputize to perform tasks, most commonly managing a user interface |
| **intent** | An `intent` is an object that a component can use to communicate with the OS. Intents are multi-purpose communication tools, and the Intent class provides different constructors depending on what you are using the intent to do. |
| **layout** | A layout defines a set of user interface objects and their position on the screen. A layout is made up of definitions written in XML. Each definition is used to create an object that appears on screen, like a button or some text. |
| **manifest** | The `manifest` is an XML file containing metadata that describes your application to the Android OS. The file is always named `AndroidManifest.xml`, and it lives in the `app/manifests` directory of your project. |
| **style** | A `style` is an XML resource that contains attributes that describe how a widget should look and behave. |
| **theme** | A `theme` is a collection of styles. Structurally, a theme is itself a style resource whose attributes point to other style resources. |
| **toast** | A *toast* is a short message that informs the user of something but does not require any input or action. |
| **widget** | Widgets are the building blocks you use to compose a user interface. A widget can show text or graphics, interact with the user, or arrange other widgets on the screen. |
| **FrameLayout** | FrameLayout is the simplest ViewGroup and does not arrange its children in any particular manner. |
