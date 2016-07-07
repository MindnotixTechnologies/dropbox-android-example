# Dropbox Android Example

Demonstrates the authentication flow using a WebView (instead of redirecting to the Dropbox app or Chrome) and file upload API for Dropbox. This is just an example app, to be integrated into a client project in the future.

## Setup

First, check here to get your API keys from Dropbox for your application: https://www.dropbox.com/developers/apps

Create a `secrets.properties` file in the root of the project. Then, add your app keys to this file:

```xml
APP_KEY=xxxxxxxxxx
APP_SECRET=xxxxxxxxxx
```

These properties will be pulled into the gradle file, but will not be committed to version control, since `secrets.properties` has been ignored.

## Compiling

The project is built with gradle, so maintenence and compilation is very straightforward. 

```
$ ./gradlew assembleDebug
```

---

## License

```
Copyright 2016 Luke Klinker

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
