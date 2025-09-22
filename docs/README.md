# Bob User Guide

Bob is a simple task manager chatbot that helps you keep track of your todos, deadlines, and events.  
It runs both in the terminal (CLI) and with a graphical interface (GUI).  
Your tasks are automatically saved to disk so they persist between sessions.

## Adding deadlines

Adds a task that must be done by a certain date/time.
Bob accepts multiple date formats, such as yyyy-MM-dd HHmm, yyyy-MM-dd, d/M/yyyy HHmm, d/M/yyyy.

Example:

deadline return book /by 2019-12-02 1800


Expected output:

Got it. I've added this task:
  [D][ ] return book (by: Dec 2 2019, 6:00PM)
Now you have 2 tasks in the list.

## Adding todos

Adds a simple task with only a description.

Example:

todo borrow book


Expected output:

Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 task in the list.

## Adding events

Adds a task with a start and end time.

Example:

event project meeting /from Mon 2pm /to 4pm


Expected output:

Got it. I've added this task:
  [E][ ] project meeting (at: Mon 2pm /to 4pm)
Now you have 3 tasks in the list.

## Listing tasks 

Shows all tasks in the list.

Example:

list


Expected output:

Here are the tasks in your list:
1. [T][ ] borrow book
2. [D][ ] return book (by: Dec 2 2019, 6:00PM)
3. [E][ ] project meeting (at: Mon 2pm /to 4pm)

## Marking and unmarking tasks 

Mark a task as done or undo it.

Example:

mark 2
unmark 2

## Deleting tasks 

Remove a task permanently.

Example:

delete 3

## Editing tasks (C-Update feature) 

You can edit the description, deadline, or timeslot of an existing task.

Examples:

edit 2 desc Borrow textbook
edit 3 by 2019-12-02 1800
edit 4 timeslot Tue 10am /to 12pm

## Exiting 

Quit the program. 

Example:

bye 

## Credits

Base project structure adapted from SE-EDU resources.

Date/time parsing patterns inspired by common iP community solutions.

Additional improvements assisted by AI tools (ChatGPT).
