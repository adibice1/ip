# Dibo User Guide

## Introduction

Dibo is your personal productivity chatbot designed to help you manage tasks, deadlines, and schedules with simple text commands. Instead of juggling multiple apps, you can interact with Dibo directly through chat to add, update, and view your tasks.

Whether you’re tracking assignments, planning events, or just making sure you don’t forget the little things, Dibo is here to keep you on top of your day.

---

## Adding ToDos

Dibo lets you attach todos to your tasks so you never forget.

**Usage:**
```
todo <task description> 
```

**Example:**

```
todo submit CS2040S assignment 
```

**Expected Outcome:**

```
=================================================================
Got it. I’ve added this task: 
[T][ ] submit CS2040S assignment 
Now you have 5 tasks in your list.
=================================================================
```

---

## Adding Deadlines

Dibo lets you attach deadlines to your tasks so you never miss an important date.

**Usage:**  
```
deadline <task description> /by <yyyy-mm-dd>
```

**Example:**  

```
deadline submit CS2040S assignment /by 2025-09-30
```

**Expected Outcome:**  

```
=================================================================
Got it. I’ve added this task: 
[D][ ] submit CS2040S assignment (by: Sep 30 2025, 11:59pm)
Now you have 5 tasks in your list.
=================================================================
```

---

## Adding Events

Dibo lets you attach events to your tasks so you know its exact timeline.

**Usage:**
```
event <task description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>
```

**Example:**

```
event do CS2040S assignment /from 2025-09-30 /to 2025-10-01
```

**Expected Outcome:**

```
=================================================================
Got it. I’ve added this task: 
[E][ ] do CS2040S assignment (from: Sep 30 2025, 11:59pm to: Oct 01 2025, 11:59pm)
Now you have 5 tasks in your list.
=================================================================
```


---

## Marking and Unmarking Tasks

You can mark tasks as **done** or set them back to **not done** when needed.

**Usage:**  

```
mark <task number>
unmark <task number>
```

**Example:**  

```
mark 1
```

**Expected Outcome:**

```
=================================================================
Nice! I’ve marked this task as done:
[T][X] read Chapter 5 of Algorithms book
=================================================================
```
**Example:**

```
unmark 1
```

**Expected Outcome:**

```
=================================================================
OK, I’ve marked this task as not done yet:
[T][ ] read Chapter 5 of Algorithms book
=================================================================
```

---

## Viewing Your Schedule

Dibo can display your tasks in the form of a schedule, making it easier to see what’s coming up.

**Usage:**  
```
schedule <date>
```
**Example:**

```
schedule 2025-09-30
```
**Expected Outcome:**

```
Schedule for Tuesday, Sep 30 2025:
=================================================================
1. [T][ ] submit CS2040S assignment (by: Sep 30 2025, 11:59pm)
2. [E][ ] project meeting with team (from: Sep 30 2025, 11:00am to: Sep 30 2025, 13:00pm)[11:00 am - 13:00 pm]
=================================================================
Total tasks: 2
```

---

## Viewing Your Tasks

Dibo can display your tasks in the form of a list, making it easier to see the total number of tasks.

**Usage:**
```
list
```
**Example:**

```
list
```
**Expected Outcome:**

```
Here are the tasks in your list:
=================================================================
1. [D][ ] submit CS2040S assignment (by: Sep 30 2025, 11:59pm)
2. [E][ ] project meeting with team (from: Sep 30 2025, 11:00am to: Sep 30 2025, 13:00pm)[11:00 am - 13:00 pm]
3. [T][ ] clean room
4. [D][ ] submit CS2100 assignment (by: Sep 25 2025, 11:59pm)
=================================================================
```

---

## Finding Your Tasks

Dibo can find any related tasks from a given word.

**Usage:**
```
find <any word in task name>
```
**Example:**

```
find assignment
```
**Expected Outcome:**

```
Here are the matching tasks in your list:
1. [D][ ] submit CS2040S assignment (by: Sep 30 2025, 11:59pm)
2. [D][ ] submit CS2100 assignment (by: Sep 25 2025, 11:59pm)
```

---

## Deleting Tasks

You can delete tasks when done.

**Usage:**

```
delete <task number>
```

**Example:**

```
delete 1
```

**Expected Outcome:**

```
=================================================================
Noted. I've removed this task:
[T][ ] read Chapter 5 of Algorithms book
Now you have 4 tasks in the list.
=================================================================
```

---

## Exiting Dibo

Say bye to Dibo! Dibo will close after 3 seconds and saves your tasks.

**Usage:**

```
bye
```

**Example:**

```
bye
```

**Expected Outcome:**

```
Bye. Hope to see you again soon!
```
