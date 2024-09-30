# My First Calendar 

## Overview

This is the **My First Calendar** project. This console-based calendar system allows users to view, create, and manage events. The program is designed to use **Java 8's Date and Time API** to handle event scheduling, displaying, and time manipulation.



### Objectives
- Use **Java 8 Date and Time API** (from the `java.time` package) to manipulate calendar data.
- Design a console-based calendar application with **at least 4 classes**: 
  - `MyCalendarTester` (with the main method)
  - `MyCalendar` (for managing events)
  - `TimeInterval` (to represent event time intervals)
  - `Event` (to represent events with a name and time interval)
- Implement functionality for both **one-time** and **recurring events**.
- Develop a system that can load, manage, and save events via text files.

---

## Features

### 1. Calendar Display
- On startup, the calendar shows the **current month** and highlights today's date using brackets `[ ]`.
- Example:
  ```plaintext
  August 2024
  Su Mo Tu We Th Fr Sa
             1  2  3
  4  5  6  7  8  9 10
  11 12 13 14 15 16 17
  18 19 20 21 [22] 23 24
  25 26 27 28 29 30 31
