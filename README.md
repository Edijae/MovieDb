# MovieDb

Simple android app that fetches and shows movies from moviedb server.

Architectural pattern used is MVVM since it's the industry recommended one when developing android apps.

Libraries used are

Glide - Glide is a fast and efficient image loading library for Android focused on smooth scrolling. Glide offers an easy to use API, a performant and extensible resource decoding pipeline and automatic resource pooling.

Retrofit - A type-safe REST client for android. It supports request cancellation, post requests and multipart uploads. It also supports both synchronous and asynchronous network requests and dynamic URLs.

Hilt - Google recommended dependency injection library. it frees you from writing tedious and error-prone boilerplate code by: Generating the AppContainer code (application graph) that you manually implemented in the manual DI section.

Gson - Gson library provides a powerful framework for converting between JSON strings and Java objects. This library helps to avoid needing to write boilerplate code to parse JSON responses yourself.

The app only shows some of the movies details. Other details such as actors, reviews could not be implemented due to time constraint.
