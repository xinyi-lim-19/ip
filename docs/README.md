
# Bob User Guide

![Ui](Ui.png)

## Introduction
Bob is a simple task management chatbot that helps you keep track of todos, deadlines, and events. It runs in a chat-style interface where you type commands and Bob responds.

---

## Quick Start

1. **Download the JAR**  
   - Ensure you have Java 17 or later installed.  
   - Run Bob with:  
     java -jar bob.jar

2. **Type a command** and press <kbd>Enter</kbd>.  
   - Example:  
     ```
     todo read book
     ```

3. **Exit** by typing:  
bye

---

## Features

### Add a ToDo
Adds a task without a date.  
todo <description>

Example:
todo read book

### Add a Deadline
Adds a task with a due date.  
deadline <description> /by <date>

Example:
deadline return book /by 2025-10-15

### Add an Event
Adds a task with a start and end date/time.  
event <description> /from <start> /to <end>

Example:
event project meeting /from 2025-10-01 /to 2025-10-02

### List Tasks
Shows all the tasks currently tracked.  
list

### Mark/Unmark Tasks
- Mark task as done:  
mark <task number>

- Mark task as not done:  
unmark <task number>

### Delete a Task
Deletes a task by its index.  
delete <task number>

### Find Tasks
Finds tasks that contain a keyword.  
find <keyword>

Example:
find book

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
