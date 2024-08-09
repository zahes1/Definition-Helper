import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.*;

public class DefinitionHelper {
    private static final String PRESETS_FILE = "presets.txt"; // presets file to store word/definition presets
    private static ArrayList<String> presetNames = new ArrayList<>();
    private static ArrayList<ArrayList<String>> wordPresets = new ArrayList<>();
    private static ArrayList<ArrayList<String>> defPresets = new ArrayList<>();
    private static ArrayList<String> currentWords = null;
    private static ArrayList<String> currentDefinitions = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to load presets? (yes/no)"); // ask if the user wants to load a preset, if there
                                                                     // is none take them to the main menu
        String loadChoice = scanner.nextLine().trim().toLowerCase();
        if (loadChoice.equals("yes") || loadChoice.equals("y")) {
            loadPresetsMenu(scanner);
        } else {
            inputWords(scanner);
        }

        scanner.close();
    }

    public static void inputWords(Scanner scanner) { // allows the user to input word/definition pairs
        boolean keepInputting = true;
        System.out.println("Type 'exit' when you are done inputting definitions.");

        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> definitions = new ArrayList<>();

        while (keepInputting) {
            System.out.println("Input word: ");
            String newWord = scanner.nextLine().trim();
            if (newWord.equalsIgnoreCase("exit")) {
                keepInputting = false;
                continue;
            } else {
                words.add(newWord);
            }

            System.out.println("Input definition: ");
            String newDef = scanner.nextLine().trim();
            if (newDef.equalsIgnoreCase("exit")) {
                System.out.println("Please enter the definition for the previous word, then you may exit."); // make
                                                                                                             // sure
                                                                                                             // each
                                                                                                             // word has
                                                                                                             // a
                                                                                                             // definition
                System.out.println("Input definition in English: ");
                newDef = scanner.nextLine().trim();
            }
            definitions.add(newDef);
        }

        currentWords = new ArrayList<>(words);
        currentDefinitions = new ArrayList<>(definitions);

        randomizeDefinitions(currentWords, currentDefinitions);
        displayMainMenu(scanner);
    }

    public static void randomizeDefinitions(ArrayList<String> words, ArrayList<String> definitions) { // randomize the
                                                                                                      // order of the
                                                                                                      // words
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices); // randomizes the indices

        ArrayList<String> shuffledWords = new ArrayList<>();
        ArrayList<String> shuffledDefinitions = new ArrayList<>();

        for (int index : indices) { // pairs the words/definitions to a new index
            shuffledWords.add(words.get(index));
            shuffledDefinitions.add(definitions.get(index));
        }

        words.clear();
        definitions.clear();

        words.addAll(shuffledWords);
        definitions.addAll(shuffledDefinitions);
    }

    public static void quizUser(ArrayList<String> words, ArrayList<String> definitions, Scanner scanner) {
        boolean quizComplete = false;
        int score = 0;
        int totalWords = words.size();

        if (words.isEmpty()) { // make sure the user has words entered, display the main menu if not
            System.out.println("There are no words currently inputted or loaded, please input words or load a preset.");
            displayMainMenu(scanner);
            return;
        }

        while (!quizComplete) {
            System.out.println("Quiz time! What do the following words mean?");

            int index = 0;

            while (index < words.size()) {
                String currentWord = words.get(index);
                String currentDefinition = definitions.get(index);

                System.out.println("What does '" + currentWord + "' mean?");
                String answer = scanner.nextLine().trim();

                if (answer.equalsIgnoreCase(currentDefinition)) {
                    System.out.println("Correct!");
                    index++;

                    if (index < (totalWords + 1)) { // check to see if they got the answer right on their first try

                        System.out.println("total words" + totalWords);
                        score++; // increase score if they got the question right on the first try
                        System.out.println("score" + score);
                    }

                } else {
                    System.out.println("Incorrect. The correct definition is: " + currentDefinition); // show them the
                                                                                                      // right answer
                                                                                                      // and move the
                                                                                                      // words to the
                                                                                                      // back of the
                                                                                                      // arraylist

                    String wordToMove = words.remove(index);
                    String defToMove = definitions.remove(index);
                    words.add(wordToMove);
                    definitions.add(defToMove);
                    index++;
                }
            }

            System.out.println("Quiz complete! You have mastered all the words.");
            System.out.println("You got " + score + "/" + totalWords + " words correct on your first try!");
            System.out.println("-----------------------");
            displayMainMenu(scanner);
            quizComplete = true;
        }
    }

    public static void displayMainMenu(Scanner scanner) { // displays the main menu
        System.out.println("Main Menu:");
        System.out.println("1. Quiz yourself!");
        System.out.println("2. Enter a new list of words");
        System.out.println("3. Manage presets (load, save, or delete)");
        System.out.println("4. Quit");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                if (currentWords != null && !currentWords.isEmpty()) {
                    randomizeDefinitions(currentWords, currentDefinitions);
                    quizUser(currentWords, currentDefinitions, scanner);
                } else {
                    System.out.println("Please enter a new list of words first."); // make sure there are words in the
                                                                                   // current list
                    inputWords(scanner);
                }
                break;
            case "2":
                inputWords(scanner);
                break;
            case "3":
                managePresetsMenu(scanner);
                break;
            case "4":
                System.exit(0); // exit the program
            default:
                System.out.println("Invalid input. Please enter '1', '2', '3', or '4'.");
                displayMainMenu(scanner);
                break;
        }
    }

    public static void managePresetsMenu(Scanner scanner) { // manages the preset menu
        System.out.println("Presets Menu:");
        System.out.println("1. Save current preset");
        System.out.println("2. Load a preset");
        System.out.println("3. Delete a preset");
        System.out.println("Type 'back' to return to the main menu.");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                if (currentWords != null && !currentWords.isEmpty()) {
                    if (presetExists(currentWords, currentDefinitions)) { // if there is already a preset with these
                                                                          // words/definitions saved
                        System.out.println(
                                "This preset already exists. Please enter new words and definitions before saving.");
                        displayMainMenu(scanner); // return to main menu after showing message
                    } else {
                        savePreset(scanner);
                    }
                } else {
                    System.out.println("No words to save. Please enter a new list of words first.");
                    inputWords(scanner);
                }
                break;
            case "2":
                loadPresetsMenu(scanner);
                break;
            case "3":
                if (!presetNames.isEmpty()) {
                    listPresets(); // display current presets
                    deletePreset(scanner);
                } else {
                    System.out.println("No presets available to delete.");
                    displayMainMenu(scanner);
                }
                break;
            case "back":
                displayMainMenu(scanner);
                break;
            default:
                System.out.println("Invalid choice. Please enter '1', '2', '3', or 'back'.");
                managePresetsMenu(scanner);
                break;
        }
    }

    public static void listPresets() {
        System.out.println("Available presets:");
        if (presetNames.isEmpty()) {
            System.out.println("No presets available.");
        } else {
            for (int i = 0; i < presetNames.size(); i++) { // loop through all the preset names
                System.out.println((i + 1) + ". " + presetNames.get(i));
            }
        }
    }

    public static void savePreset(Scanner scanner) { // lets the user choose the name for their preset
        String presetName = "";

        while (true) {
            System.out.println("Enter a name for the preset:");
            presetName = scanner.nextLine().trim();

            if (presetName.equalsIgnoreCase("back")) { // dont let them have the name back
                System.out.println("The name 'back' is not allowed. Please choose a different name.");
                continue;
            }

            boolean nameExists = false;
            for (String name : presetNames) { // check current preset names to make sure they dont have duplicates
                if (name.equalsIgnoreCase(presetName)) {
                    System.out
                            .println("You already have a preset named " + presetName + ". Please enter another name.");
                    nameExists = true;
                    break;
                }
            }

            if (!nameExists) {
                break;
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(PRESETS_FILE, true))) { // writes to the presets.txt
                                                                                         // file
            writer.println("Preset Name: " + presetName);
            for (int i = 0; i < currentWords.size(); i++) { // loops through all the word/definition pairs and writes
                                                            // them in the file
                writer.println(currentWords.get(i) + ":" + currentDefinitions.get(i));
            }
            writer.println(); // Add an empty line to separate presets
        } catch (IOException e) {
            System.out.println("An error occurred while saving the preset.");
            e.printStackTrace();
        }

        presetNames.add(presetName);
        wordPresets.add(new ArrayList<>(currentWords));
        defPresets.add(new ArrayList<>(currentDefinitions));
        System.out.println("Preset saved successfully!");

        displayMainMenu(scanner);
    }

    public static void loadPresetsMenu(Scanner scanner) {
        System.out.println("Loading presets...");
        loadPresetsFromFile();
        if (presetNames.isEmpty()) { // if there are no presets
            System.out.println("No presets available to load. Please save a preset first.");
            displayMainMenu(scanner);
            return;
        }

        listPresets(); // lists the presets on screen

        System.out.println(
                "Enter the name or number of the preset you want to load, or 'back' to return to the main menu:");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("back")) {
            displayMainMenu(scanner);
            return;
        }

        int presetIndex = -1;
        try { // attempt to load the preset given by the user
            presetIndex = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            presetIndex = presetNames.indexOf(input);
        }

        if (presetIndex >= 0 && presetIndex < presetNames.size()) {
            currentWords = new ArrayList<>(wordPresets.get(presetIndex));
            currentDefinitions = new ArrayList<>(defPresets.get(presetIndex));
            System.out.println("Preset loaded successfully!");
        } else {
            System.out.println("Invalid preset name or number. Please try again.");
        }

        displayMainMenu(scanner);
    }

    public static void loadPresetsFromFile() {
        presetNames.clear();
        wordPresets.clear();
        defPresets.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(PRESETS_FILE))) {
            String line;
            String presetName = null;
            ArrayList<String> words = null;
            ArrayList<String> definitions = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Preset Name: ")) {
                    if (presetName != null && words != null && definitions != null) {
                        presetNames.add(presetName);
                        wordPresets.add(words);
                        defPresets.add(definitions);
                    }

                    presetName = line.substring(13);
                    words = new ArrayList<>();
                    definitions = new ArrayList<>();
                } else if (line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2 && words != null && definitions != null) {
                        words.add(parts[0].trim()); // adds the words
                        definitions.add(parts[1].trim()); // adds the definitions
                    }
                }
            }

            if (presetName != null && words != null && definitions != null) {
                presetNames.add(presetName);
                wordPresets.add(words);
                defPresets.add(definitions);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading the presets.");
            e.printStackTrace();
        }
    }

    public static void deletePreset(Scanner scanner) { // deletes a currently stored preset
        System.out.println(
                "Enter the name or number of the preset you want to delete, or 'back' to return to the main menu:");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("back")) {
            displayMainMenu(scanner);
            return;
        }

        int presetIndex = -1;
        try {
            presetIndex = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            presetIndex = presetNames.indexOf(input);
        }

        if (presetIndex >= 0 && presetIndex < presetNames.size()) {
            presetNames.remove(presetIndex);
            wordPresets.remove(presetIndex);
            defPresets.remove(presetIndex);
            savePresetsToFile();
            System.out.println("Preset deleted successfully!");
        } else {
            System.out.println("Invalid preset name or number. Please try again.");
        }

        displayMainMenu(scanner);
    }

    public static void savePresetsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PRESETS_FILE))) {
            for (int i = 0; i < presetNames.size(); i++) {
                writer.println("Preset Name: " + presetNames.get(i));
                ArrayList<String> words = wordPresets.get(i);
                ArrayList<String> definitions = defPresets.get(i);
                for (int j = 0; j < words.size(); j++) {
                    writer.println(words.get(j) + ":" + definitions.get(j));
                }
                writer.println(); // Add an empty line to separate presets
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the presets.");
            e.printStackTrace();
        }
    }

    public static boolean presetExists(ArrayList<String> words, ArrayList<String> definitions) {
        for (int i = 0; i < wordPresets.size(); i++) {
            ArrayList<String> savedWords = wordPresets.get(i);
            ArrayList<String> savedDefinitions = defPresets.get(i);
            if (savedWords.equals(words) && savedDefinitions.equals(definitions)) {
                return true;
            }
        }
        return false;
    }
}
