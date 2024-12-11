# Definition Helper

## Overview
Definition Helper is a Java-based application that helps users learn and memorize words and their definitions. It allows users to:
- Enter word-definition pairs.
- Randomize and quiz themselves on the pairs.
- Track their performance with a score based on correct answers on the first attempt.
- Save, load, and manage preset word-definition lists for future use.

This program is designed to make studying vocabulary efficient and engaging, offering functionalities like a randomized quiz mode and a persistent storage mechanism for presets.

## Features
- **Interactive Quizzes**: Test yourself on entered word-definition pairs with randomized order to ensure robust learning.
- **Preset Management**: Save, load, and delete word-definition presets for easy reuse.
- **Performance Tracking**: Keep track of your score to measure improvement over time.
- **User-Friendly Interface**: Simple command-line interface with menus for intuitive navigation.

## Project Structure
- **DefinitionHelper.java**:  
  The main application file. Implements the core functionalities including quiz mode, preset management, and user interaction.

- **presets.txt**:  
  Stores saved presets in a structured format for persistent storage and retrieval.

## How to Run
1. **Prerequisites**:  
   Ensure you have Java (JDK) installed on your system. You can download it from [Oracle's official website](https://www.oracle.com/java/technologies/javase-downloads.html).

2. **Compilation and Execution**:
   - Download the `DefinitionHelper.java` file and place it in a directory on your computer.
   - Open a terminal or command prompt, navigate to the directory containing the `DefinitionHelper.java` file, and compile the program:
     ```bash
     javac DefinitionHelper.java
     ```
   - Once compiled, run the application:
     ```bash
     java DefinitionHelper
     ```

   After running, the program will guide you through the process of either loading an existing preset or inputting new words and definitions.

## Usage

**Main Menu Options:**
- **1. Quiz yourself!**:  
  Begins the quiz using the currently loaded words.  
- **2. Enter a new list of words**:  
  Allows you to input custom word-definition pairs.
- **3. Manage presets**:  
  Save the current list of words as a preset, load a previously saved preset, or delete a preset.
- **4. Quit**:  
  Exits the application.

**Presets Menu Options:**
- **1. Save current preset**:  
  Saves the current words and definitions into `presets.txt` under a chosen name.
- **2. Load a preset**:  
  Loads a previously saved preset into the current session.
- **3. Delete a preset**:  
  Removes a saved preset from the file.
- **Back**:  
  Returns to the main menu.

## Tips and Best Practices
- **Backup Your Presets**: Keep a backup of your `presets.txt` file to safeguard against data loss.
- **Use Descriptive Names**: Give presets descriptive names (e.g., "French Vocabulary - Basic Nouns") for easy identification.
- **Regular Review**: Test yourself regularly to reinforce your memory and track your improvement over time.

