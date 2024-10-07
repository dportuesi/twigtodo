# Twig To-Do
A simple To-do app in android built with MVVM best practices, Room, and Compose.

![record](https://i.giphy.com/media/v1.Y2lkPTc5MGI3NjExanZtYXB4YjBjbjd1bm9qbnFxZmU0ejRlY2pnMmR0czJ6dHVnZ2ticSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/49dqspjjuocjkvOIOp/giphy.gif)


## Screenshots
<p float="left">
  <img src="Screenshot_1.png" width=33% height=50%>
  <img src="Screenshot_2.png" width=33% height=50%>
</p>

## Architecture

This app built using MVVM, using Room as the database manager and Koin as the dependency injection.

The data flow is as follows:

Database -> DTO Object -> Repository -> Domain Object -> View Model -> View

For the ToDo list itself, the database is the ultimate source of truth, and the repository
provides a listener for the view to listen on.

## Testing

Just viewmodels have been unit testing using Junit4 and mockk. Please see ![HomeViewModelTest](https://github.com/dportuesi/twigtodo/blob/main/app/src/test/java/com/branchapp/twigtodo/HomeViewModelTest.kt).
