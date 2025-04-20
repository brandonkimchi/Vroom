# Vroom

<p align="center">
  <img src="https://github.com/user-attachments/assets/2ce1a2dd-d333-459f-8ddb-468f367d9c94" alt="Vroom" width="400"/>
</p>

Vroom is an android application developed in Java to help students in Singapore easily find and connect with reputable private driving instructors. Many learners prefer private instruction due to lower costs and flexible schedules, but they often struggle with fragmented platforms, potential scams, and unreliable booking systems. Vroom addresses these issues by offering a secure, centralized platform where students can search for instructors based on price, location, and availability.

Key features include instructor discovery, booking lessons, scheduling, in-app chat, lesson reminders, and theory test quizzes. The app uses Firebase as its backend (NoSQL), storing user profiles, bookings, and chat histories efficiently. To simulate conversing with Singaporean driving instructors in the prototype, we used prompt engineering with OpenAI's GPT-3.5 Turbo Large Language Model (LLM) in the in-app chat feature.

Advanced algorithms like Dijkstra’s algorithm are used to calculate the shortest path between towns, helping students find nearby instructors. Data structures such as HashMaps and ArrayLists support fast data access and dynamic UI updates, particularly within RecyclerViews.

Vroom’s design follows core software engineering principles including Single Responsibility and Composition Over Inheritance, ensuring modular, maintainable code. Vroom delivers a seamless experience that simplifies the learning journey and enhances communication between students and instructors.
