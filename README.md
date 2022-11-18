# ExplorEat

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Testing](#testing)

## Introduction
ExplorEat is a simple app that holds a map where the user can move the map and when the move is stopped, an API call to [Foursquare Places API](https://developer.foursquare.com/reference/place-search) to fetch all the food places near to user's location. This app developed using MVVM architecture, with Coroutines and LiveData. The [Foursquare Places Photos API](https://developer.foursquare.com/reference/place-photos) is also used to fetch images for the places.

## Features
The app consists of two modes:
- Exploration Mode
- Navigation Mode

The app starts in Exploration Mode and the user is able to move the map and see the places near to marker's position. When the user clicks on a place from the list at the bottom view, then the app goes to the Navigation Mode where the user is able to see a new view at the top of the screen with informations about the place and also is able to see the place's location with a red marker. Once the user clicks the back button while in Navigation Mode, the app goes to the Exploration Mode again. Take a quick look at the screenshots below!

![Exploration Mode](/screenshots/exploreat_places_exploration.png)
![Navigation Mode](/screenshots/exploreat_place_navigation.png)

## Testing
This project contains [Instrumented](/app/src/androidTest) and [Unit](/app/src/test/java/com/example/exploreat) tests for some cases.
