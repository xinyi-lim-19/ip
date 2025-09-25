---
layout: default
title: Bob User Guide
---

# Bob User Guide

![Ui](Ui.png)

## Introduction
Bob is a simple task management chatbot that helps you keep track of todos, deadlines, and events. It runs in a chat-style interface where you type commands and Bob responds.

---

## Quick Start

1. **Download the JAR**  
   - Ensure you have Java 17 or later installed.  
   - Run Bob with:  
     ```bash
     java -jar bob.jar
     ```

2. **Type a command** and press <kbd>Enter</kbd>.  
   - Example:  
     ```
     todo read book
     ```

3. **Exit** by typing:  
bye

yaml
Copy code

---

## Features

### Add a ToDo
Adds a task without a date.  
todo <description>

makefile
Copy code
Example:
todo read book

css
Copy code

### Add a Deadline
Adds a task with a due date.  
deadline <description> /by <date>

makefile
Copy code
Example:
deadline return book /by 2025-10-15

pgsql
Copy code

### Add an Event
Adds a task with a start and end date/time.  
event <description> /from <start> /to <end>

makefile
Copy code
Example:
event project meeting /from 2025-10-01 /to 2025-10-02

python
Copy code

### List Tasks
Shows all the tasks currently tracked.  
list

bash
Copy code

### Mark/Unmark Tasks
- Mark task as done:  
mark <task number>

bash
Copy code
- Mark task as not done:  
unmark <task number>

pgsql
Copy code

### Delete a Task
Deletes a task by its index.  
delete <task number>

css
Copy code

### Find Tasks
Finds tasks that contain a keyword.  
find <keyword>

makefile
Copy code
Example:
find book

perl
Copy code

### Save Data Automatically
Bob saves your tasks to disk after every change. Next time you run the app, your tasks will be loaded automatically.

---

## Command Summary

| Command                                | Example                           |
|----------------------------------------|-----------------------------------|
| `todo <desc>`                          | `todo read book`                  |
| `deadline <desc> /by <date>`           | `deadline return book /by 2025-10-15` |
| `event <desc> /from <start> /to <end>` | `event project meeting /from 2025-10-01 /to 2025-10-02` |
| `list`                                 | `list`                            |
| `mark <n>`                             | `mark 2`                          |
| `unmark <n>`                           | `unmark 2`                        |
| `delete <n>`                           | `delete 3`                        |
| `find <keyword>`                       | `find book`                       |
| `bye`                                  | `bye`                             |

---

📌 **Tip**: The screenshot above (`Ui.png`) shows the full Bob GUI window, including the product name in the title bar.
MD
