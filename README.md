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



* * * 

## Glossary
*(running list)*

| Term | Info |
| ---- | ---- |
| **activity** | An activity is an instance of Activity, a class in the Android SDK. An activity is responsible for managing user interaction with a screen of information. |
| **layout** | A layout defines a set of user interface objects and their position on the screen. A layout is made up of definitions written in XML. Each definition is used to create an object that appears on screen, like a button or some text. |
| **toast** | A *toast* is a short message that informs the user of something but does not require any input or action. |
| **widget** | Widgets are the building blocks you use to compose a user interface. A widget can show text or graphics, interact with the user, or arrange other widgets on the screen. |
