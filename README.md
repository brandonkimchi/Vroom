# Vroom

<p align="center">
  <img src="https://github.com/user-attachments/assets/2ce1a2dd-d333-459f-8ddb-468f367d9c94" alt="Vroom" width="400"/>
</p>

Vroom is an android application developed in Java to help students in Singapore easily find and connect with reputable private driving instructors. Many learners prefer private instruction due to lower costs and flexible schedules, but they often struggle with fragmented platforms, potential scams, and unreliable booking systems. Vroom addresses these issues by offering a secure, centralized platform where students can search for instructors based on price, location, and availability.

Key features include instructor discovery, booking lessons, scheduling, in-app chat, lesson reminders, and theory test quizzes. The app uses Firebase as its backend (NoSQL), storing user profiles, bookings, and chat histories efficiently. To simulate conversing with Singaporean driving instructors in the prototype, we used prompt engineering with OpenAI's GPT-3.5 Turbo Large Language Model (LLM) in the in-app chat feature.

Advanced algorithms like Dijkstra’s algorithm are used to calculate the shortest path between towns, helping students find nearby instructors. Data structures such as HashMaps and ArrayLists support fast data access and dynamic UI updates, particularly within RecyclerViews.

Vroom’s design follows core software engineering principles including Single Responsibility and Composition Over Inheritance, ensuring modular, maintainable code. Vroom delivers a seamless experience that simplifies the learning journey and enhances communication between students and instructors.
<br>

*Some random information about the project:*

![image](https://github.com/user-attachments/assets/dfb76ef9-03ca-4427-83de-e715dd060431)


![WhatsApp Image 2025-03-18 at 02 45 17_e521aa0a](https://github.com/user-attachments/assets/4fc7b7f7-4dfb-4eb6-ad54-f6a65a056e10)


Undirected Weighted Graph Data Structure is drawn based on eyeballing this Singapore with the towns and doing aggaration on the distances (weights), which we then applied Dijkstra's Algorithm. (rmb save a copy of this image in the repo somewhere)

Bottom sketch is my draft interpretation of the Undirected Weighted Graph Data Structure.

Distance ARE NOT ACCURATE! Its just to draft up a model to demonstrate the feature of distance sorting.

<br>

Random personal notes on Android App Development with Java:
Android/iOS App development (not just with Java but can be for dart, swift, etc) terminology:
- Each button on the phone is called an app
- Each page in these app is called an 'Activity'
- the first page that opens when you click open an app is called the 'Main Activity' (you can think of it as the homepage of the app)
- intent - the transfer of data from 1 activity/page of the app to another
- Each component on the app (e.g. buttons, texts) is called a 'View'

- An XML language file is like the HTML and CSS language with JavaScript for Android app development with Java. It describes the layout and design (front-end) of the android app pages/activity
- Different 'Views' will have different attributes. (e.g. A text 'View' will have attributes like 'text' and 'clickable', while a button 'View' might have other types of attributes like 'onClick', and so does the other types of 'Views' will have all different types of attributes)
- An important attribute that exists in all types of 'Views' is called the 'ID' attribute. Since in android app development we would often require to reference 'Views' so we can modify or work with it inside of code. And in order to do things dynamically like that, we often want to interact with these 'Views', which we can do so by stating their 'ID' attributes
- A Fragment is a reusable UI component that represents a portion of a screen inside an Activity. Fragments allow for modular design, meaning you can break your UI into smaller, manageable pieces. Think of it like the 'Activity' → The container (full screen, while the 'Fragment' → A piece of that screen (like a section in a webpage)

<br>

Presentation slides to pitch the project idea:
- https://www.canva.com/design/DAGe-dY8S3w/s84h1FwiLnhPeZOdrkp3uw/edit (Design by my groupmates not me, I am just the implementor)

Figma link:
- https://www.figma.com/design/rPCWeufByW9ThzQH79vdi2/Vroom?node-id=0-1&t=e2KqXYXxkes1UtQT-1 (Design by my groupmates not me, I am just the implementor)

Firebase Realtime Database link:
- https://console.firebase.google.com/u/0/project/vroom-android-application/database/vroom-android-application-default-rtdb/data/~2F (Firebase Realtime Database) (however its only accessible by me from my Gmail account)

Website links that helped me with debugging various bugs:
- https://stackoverflow.com/questions/76018702/i-cant-create-a-new-project-with-java-language-anymore-on-android-studio-flamin

How upload code from Android Studio to Github with Git:
- https://www.youtube.com/watch?v=VJOblwM2KJ0&t=8s

Sources:
- https://www.youtube.com/watch?v=tZvjSl9dswg ('Android App Development in Java All-in-One Tutorial Series (4 HOURS!)' Youtube video by Caleb Curry) (For learning syntax basics of Android App Development in Java in Android Studio)
- https://www.youtube.com/watch?v=aiX8bMPX_t8&t=230s ('How to connect Firebase to Android Studio App | 2024' Youtube video by Easy Tuto) (For learning how to connect Firebase to Android Studio IDE)

<br>

## How to push code from Android Studio to Github?
1.
```text
git init
```

2.
```text
git remote add origin https://github.com/WindJammer6/Vroom.git
```

3.
```text
git add .
```

4.
```text
git commit -am "[Commit message]"
```

5.
```text
git push origin master
```

OR

```text
git push origin master --force
```

<br>

## About deployment
At the end of this project I might consider publishing this app officially as a side hustle (Jetwei) Source on how this might be done: https://developer.android.com/studio/publish
