# News Search Application Assessment

This is an Android Application, allowing its users to be able to search news about anything they wish to know.

The application provides the user with any news articles that have been published on the search date. If there are no published articles related to the search, then the user is notified.

All the articles provided will also have the user's search input in the article's title and they will be all written in the English language.

-------

The Application was developed in native Android UI using the MVVM Architecture, Retrofit for the News API and Dagger Hilt for Dependecy Injection.

The Text to Speech button is meant to provide a short description of the article selected, however I was not able to find a way to retrieve the description from the API as it kept on providing me with a null value. Therfore, I showed the functionality of Text to Speech by allowing the application to read out the title of the article.

I believe newsapi.org used to provide the description of an article and an image of the article as an url, but they no longer provide it for the "everything" endpoint, which is the API endpoint that I needed and used for the GET requests.

-----

The following video is a visual representation of the application:

https://github.com/Mosfeq/News-Search-Application-Assessment/assets/86776254/d3b98462-384b-4743-99b6-f610f1bad4e4

