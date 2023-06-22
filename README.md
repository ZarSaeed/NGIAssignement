# NGIAssignement

## Android Studio(IDE)

Development environment used for developing this assignment android app.
Runnable up to version: Android Studio Electric Eel | 2022.1.1
Kotlin 1.8.0 (Programming language)

## Android SDK levels

minSdkVersion 24
targetSdkVersion 33
compileSdkVersion 33

## How to run this code?

1. You will need Android Studio (IDEA) with android Sdk and JDK installed.

Here is a link to download android studio:
https://developer.android.com/studio?gclid=CjwKCAjwl6OiBhA2EiwAuUwWZWz2b_0F58z6S8dt_U9ZSkbbzjHUiWQinzc37Uf-prRpDYCuAkiAchoCbZEQAvD_BwE&gclsrc=aw.ds
Android studio comes up with Android SDK so no need to separately download it.

2. Once android studio successfully installed, you have to clone the app code or download zip
   folder and unzip it.

3. Open android studio you will see open project option or you can find it from the top left 'File'
   option.

4. While opening select the cloned or unzip 'folder' root folder and wait for the all gradle
   process to finish.

5. Once the build is done, select 'debug' build variant from bottom left toolbar menu if you
   can't find it search for it at top
   right search option, wait for all the build process.

6. If all the build process is successful you can run the app from green play button on top toolbar
   or you can run it from "Run" at top menu.

7. You can create a simulator from AVD manager available in top menu "Tools" option or you can also
   plugin your real device through U.S.B port, make sure usb debugging is enabled at "Developer
   Option" in settings. If you can't see developer option at settings than go to "About" and find
   Build Number
   click build number 7 times to enable "Developer Option".

## GitFlow & Latest Working Branches

Git flow is followed for branching structured.

1. main - latest code
2. dev
3. movies_listing
4. movies_details
5. unit_testing

## Code Architecture

The architecture which followed is clean architecture with separate modules(Presenter, Domain and
Data)
Modules

### PRESENTER

All the u.i related work such as screens(composables and view models) classes can
be found in the presenter module.

### DOMAIN

This module is responsible for handling the business logic.

### DATA

All data handling is done through data module, Data can be queried from network or local data store
manager by using a repository pattern.
Retrofit library used for the networking purpose.


## Dependency Injection

Hilt version 2.44 is used for injecting classes instances.
