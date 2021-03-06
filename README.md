# FoodBot App

[![Build Status](https://travis-ci.org/f2prateek/FoodBot.png?branch=master)](https://travis-ci.org/f2prateek/FoodBot)

This repository contains the source code for the [FoodBot](http://www.github.com/f2prateek/FoodBot)
Android app available from [Google Play](https://play.google.com/store/apps/details?id=com.f2prateek.foodbot).

Please see the [issues](https://github.com.f2prateek.foodbot/issues) section
to report any bugs or feature requests and to see the list of known issues.

## License
```
Copyright 2013 Prateek Srivastava (@f2prateek)
Copyright 2012 Google Inc.
Copyright 2012 Donn Felker
Copyright 2012 GitHub Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Building

The build requires [Maven](http://maven.apache.org/download.html) v3.0.3+ and the [Android SDK](http://developer.android.com/sdk/index.html) to be installed in your development environment. In addition you'll need to set the `ANDROID_HOME` environment variable to the location of your SDK:

`export ANDROID_HOME=~/tools/android-sdk`

After satisfying those requirements, the build is pretty simple:

* Run `mvn clean package` from the `app` directory to build the APK only
* Run `mvn clean install` from the root directory to build the app and also run
the integration tests, this requires a connected Android device or running
emulator
* Run `mvn clean verify` from the root directory to build the app, run the integration tests, and run checkstle

## Acknowledgements

  * [ActionBarSherlock](https://github.com/JakeWharton/ActionBarSherlock) for a
consistent, great looking header across all Android platforms,
  * [ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator)
  for swiping between fragments and
  * [NineOldAndroids](https://github.com/JakeWharton/NineOldAndroids) for 
  view animations - all from [Jake Wharton](http://jakewharton.com/).
  * [RoboGuice](http://code.google.com/p/roboguice/) for dependency-injection.
  * [Robotium](http://code.google.com/p/robotium/)
  for driving our app during integration tests.
  * [android-maven-plugin](https://github.com/jayway/maven-android-plugin)
  for automating our build and producing release-ready APKs.
  * [Android-Times-Square](https://github.com/square/android-times-square)
  * [Otto](https://github.com/square/otto)
  * [AndroidBootstrap](http://www.androidbootstrap.com/)
  * [Travis-CI](https://travis-ci.org/f2prateek/FoodBot) for continous integration

## Contributing

Please fork this repository and contribute back using [pull requests](https://github.com.f2prateek.foodbot/pulls).
