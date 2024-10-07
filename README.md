# Twig To-Do
A simple To-do app in android built with MVVM best practices, Room, and Compose.

<iframe src="https://giphy.com/embed/49dqspjjuocjkvOIOp" width="214" height="480" style="" frameBorder="0" class="giphy-embed" allowFullScreen></iframe><p><a href="https://giphy.com/gifs/49dqspjjuocjkvOIOp">via GIPHY</a></p>

<img src="Screenshot_1.png" width=50% height=50%>
<img src="Screenshot_2.png" width=50% height=50%>

## Architecture

This app built using MVVM, using Room as the database manager and Koin as the dependency injection.

The data flow is as follows:

Database -> DTO Object -> Repository -> Domain Object -> View Model -> View

For the ToDo list itself, the database is the ultimate source of truth, and the repository
provides a listener for the view to listen on.

## Testing

Just viewmodels have been unit testing using Junit4 and mockk. Please see ![HomeViewModelTest](https://github.com/dportuesi/twigtodo/blob/main/app/src/test/java/com/branchapp/twigtodo/HomeViewModelTest.kt).