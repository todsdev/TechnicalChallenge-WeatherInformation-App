<!-- # Title -->
# Weather
![Demo](https://images.squarespace-cdn.com/content/v1/5e26661cfb3e614a442d9dbc/1620567189230-5R23FSFPIDUDWAVDTXT9/Weather+hand+painted+Letters+only+dry+blood.png)


<!-- # Short Description -->

>- The application is designed to give the user useful information about the *weather*, recovering all the details for the specific *location*.
>- The user can choose from a list of cities to recover their information, including the current location of the device.
>- If none of the cities is which one you want to look for, free text the city you want to recover information. 

This application was designed to supply the needed requisites from a *Android Code Challenge*, designing an application that is able to recover 
**weather** information such as **visibility** and **humidity** from a specific location. \
The focus of this application was mainly the codes and functionalities. The interface is caring of details, animations and images but as it will be 
shown below, it works perfectly and follow the latest technologies of Android development.


<!-- # Badges -->
<div style="display: inline_block"><br>
    <img height="30" width="40" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/androidstudio/androidstudio-original.svg">
    <img height="30" width="40" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/kotlin/kotlin-original.svg">
</div>

---

# Tags

`Android Studio` `Kotlin` `GoogleMaps` `OkHTTP` `Coroutines` `MVVM` `Retrofit` `Hilt`

---

# Android Code Challenge
## Rules: 
> Description
>- The app **should** contain the 09 european cities listed and the **current** location
>- At least two different interfaces, one for *searching* and other for *information* purposes
>- Use the requested *API*


---

# Demo

First Time             |  Other Times
:-------------------------:|:-------------------------:
![](https://media.discordapp.net/attachments/655489748885831713/1046668483418665020/openingwithdialog.gif)  |  ![](https://media.discordapp.net/attachments/655489748885831713/1046668485117345842/openingwithoutdialog.gif)


>- Install your app and read a dialog explaining how to *search* locations using the app's funcionalities.
>- This dialog will be shown *only* the first time the application is open.



Current Location             |  Specific Location
:-------------------------:|:-------------------------:
![](https://media.discordapp.net/attachments/655489748885831713/1046668484278497330/searchingwithcurrent.gif)  |  ![](https://media.discordapp.net/attachments/655489748885831713/1046668483993288774/searchingwithbutton.gif)


>- Use your current location given by your GPS system to receive more *information* about your area
>- Use one of the specific cities of the list to recover their *information*
>- Check them on **map**!
  
 
 
![Demo](https://media.discordapp.net/attachments/655489748885831713/1046668483720642591/searchingwithquery.gif)


>- If none of the locations provided matches with the one you want to search, simply search by texting the name of city you want to recover *information*
>- Check it on map!


---

# Code Example
```kotlin
private fun configSharedPreferences() {
        sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE) ?: return
        val value =
            sharedPreferences.getInt(getString(R.string.readed_how_to_use), Constants.DEFAULT_VALUE)
        if (value == Constants.DEFAULT_VALUE) {
            sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPreferences.edit()) {
                putInt(getString(R.string.readed_how_to_use), Constants.UPDATED_VALUE)
                apply()
            }
            configOneTimeDialog()
        }
    }
```

Code designed to be used insite *onCreate*, which creates a *sharedPreference* with a default value.
This code creates a *dialog* explaining how to use the **search** functions to recover the information in one of the two possible ways provided.\
The code compares the value created the first time the application runs with the value updated after the dialog appears. Since the value is updated in the right first moment, it just becomes as default when the user reinstall the application, avoiding dealing with the explanation every time.

---

# Libraries

>- [Timber](https://github.com/JakeWharton/timber)
>- [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
>- [Coroutines](https://developer.android.com/kotlin/coroutines?hl=pt-br)
>- [KTX](https://developer.android.com/kotlin/ktx)
>- [Retrofit](https://square.github.io/retrofit/)
>- [OkHTTP](https://square.github.io/okhttp/)
>- [Navigation Components](https://developer.android.com/guide/navigation)
>- [Hilt](https://dagger.dev/hilt/)
>- [Google Play Services Location](https://developers.google.com/android/guides/setup)
>- [Google Play Services Maps](https://developers.google.com/maps)
>- [OpenWeather 2.5 API](https://openweathermap.org/api)

---

# Contributors

- [Thiago Rodrigues](https://www.linkedin.com/in/tods/)
