# Pop Picture

Pop Picture is a gallery application that allows users to see pictures that were taken around them, users can also see those pictures ordered by distance or number of likes.

![Pop Picture Main Screen](https://poppicture-57876.firebaseapp.com/pop_picture/home-nearby.jpg)

In the image above we can see our main screen, which is the tab where pictures are ordered by distance to the user. The app is very straight forward, the first time the user opens, we just ask for location permition so we can show exactly what's around.

![Pop Picture Onboarding](https://poppicture-57876.firebaseapp.com/pop_picture/onboarding.jpg)
![Pop Picture Onboarding Permission](https://poppicture-57876.firebaseapp.com/pop_picture/onboarding-permission.jpg)

## Behind the technology

### Pictures around you, efficiently... but how?

The Pop Picture's backend is the brain of the application, using a data structure called quadtree, the backend is able to return pictures taken around the user's location within milliseconds, even if we had millions of pictures. To see more about a Quadtree, please check my quadtree project: [GitHub](https://github.com/leonardodlana/quadtree-geolocation)

### Storage

All the content that users add into the application is stored inside the Firebase Cloud Storage, reliable, safe, fast and simple.

In the device, all downloaded thumbnails and pictures are stored inside the cache folder. The user is free to free up space anytime.

### What about sensitive content?

Using Firebase's Machine Learning we can have a list of tags that were detected in the picture's content, after that, we just have to determine a threshold of 2 labels that Firebase's ML returns: "Flesh" and "Swimwear".

In case "Flesh" returns more than 0.5, we can consider that this might be a nudity picture, then, we check for swimwear to avoid false-positive of bikinis for example. This is a "dumb" detection, but it's quite helpful. If the picture is considered to have sensitive content, a warning is shown to the user. Other users can report the picture to make it disappear.

### The view...

The viewer screen is designed to have 100% focus on the picture.

![Pop Picture View](https://poppicture-57876.firebaseapp.com/pop_picture/viewer.jpg)
![Pop Picture View Bottom](https://poppicture-57876.firebaseapp.com/pop_picture/viewer-bottom.jpg)
