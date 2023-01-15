package com.gridu;

import java.io.*;
import java.util.*;

public class Main {
    Map<String, String> flashcards = new LinkedHashMap<>();
    Map <String, Integer> hardestCardsMap = new LinkedHashMap<>();
    ArrayList<String> log = new ArrayList<>();
    String term;
    String def;
    int hardestCardCount;

    public void addCard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("The card:");
        log.add("The card:");
        term = scanner.nextLine();
        log.add(term);
        while (flashcards.containsKey(term)) {
            if (flashcards.containsKey(term)) {
                System.out.println("The card \"" + term + "\""
                        + " already exists.");
                log.add("The card \"" + term + "\""
                        + " already exists.");
                return;
            }

        }
        System.out.println("The definition of the card:");
        log.add("The definition of the card:");
        def = scanner.nextLine();
        log.add(def);
        while (flashcards.containsValue(def)) {
            if (flashcards.containsValue(def)) {
                System.out.println("The definition \"" + def + "\""
                        + " already exists.");
                log.add("The definition \"" + def + "\""
                        + " already exists.");
                return;
            }
        }
        System.out.println("The pair (\"" + term  + "\":\"" + def + "\") has been added.");
        log.add("The pair (\"" + term  + "\":\"" + def + "\") has been added.");
        flashcards.put(term, def);
        hardestCardsMap.put(term, 0);
    }

    public void removeCard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which card?");
        log.add("Which card?");
        if (flashcards.containsKey(term = scanner.nextLine())) {
            log.add(term);

            flashcards.remove(term);
            hardestCardsMap.remove(term);
            System.out.println("The card has been removed.");
            log.add("The card has been removed.");
        } else {
            log.add(term);

            System.out.println("Can't remove \"" + term + "\": there is no such card.");
        }
    }

    public void importCards() {
        System.out.println("File name:");
        log.add("File name:");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        log.add(fileName);

        String line;
        int numberCards = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/ebelousov/IdeaProjects/Flashcards/Flashcards/task/" + fileName))) {
            while ((line = reader.readLine()) != null) {
                String[] keyValuePair = line.split("=", 3);
                String key = keyValuePair[0];
                String value = keyValuePair[1];
                int mistake = Integer.parseInt(keyValuePair[2]);
                flashcards.put(key, value);
                hardestCardsMap.put(key, mistake);
                numberCards++;
            }
        } catch (IOException e) {
            System.out.println("File not found.");
            log.add("File not found.");
            return;
        }
        System.out.println(numberCards + " cards have been loaded.");
        log.add(numberCards + " cards have been loaded.");
    }

    public void exportCards() {
        System.out.println("File name:");
        log.add("File name:");
        Scanner fileName = new Scanner(System.in);
        String fileNameS = fileName.nextLine();
        log.add(fileNameS);

        File file = new File("/Users/ebelousov/IdeaProjects/Flashcards/Flashcards/task/" + fileNameS);
        BufferedWriter bf = null;
        try {
            bf = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String, String> entry :
                    flashcards.entrySet()) {
                bf.write(entry.getKey() + "="
                        + entry.getValue() + "=" + hardestCardsMap.get(entry.getKey()));
                bf.newLine();
            }
            bf.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(flashcards.size() + " cards have been saved.");
        log.add(flashcards.size() + " cards have been saved.");
    }

    public void ask() {
        System.out.println("How many times to ask?");
        log.add("How many times to ask?");
        String answer;
        Scanner scanner = new Scanner(System.in);
        int numberCards = Integer.parseInt(scanner.nextLine());
        String nc = String.valueOf(numberCards);
        log.add(nc);

        for (var entry : flashcards.entrySet()) {
            System.out.println("Print the definition of \"" + entry.getKey() + "\":");
            log.add("Print the definition of \"" + entry.getKey() + "\":");
            answer = scanner.nextLine();
            log.add(answer);
            if (answer.equals(entry.getValue())) {
                hardestCardsMap.put(entry.getKey(), hardestCardsMap.get(entry.getKey()));
                System.out.println("Correct!");
                log.add("Correct!");
            } else if (flashcards.containsValue(answer)) {
                hardestCardsMap.put(entry.getKey(), hardestCardsMap.get(entry.getKey()) + 1);
                hardestCardCount++;
                System.out.println("Wrong. The right answer is \"" + entry.getValue() + "\", but your definition is correct for \"" + getKeyByValue(flashcards, answer) + "\".");
                log.add("Wrong. The right answer is \"" + entry.getValue() + "\", but your definition is correct for \"" + getKeyByValue(flashcards, answer) + "\".");
            } else {
                hardestCardsMap.put(entry.getKey(), hardestCardsMap.get(entry.getKey()) + 1);
                hardestCardCount++;
                System.out.println("Wrong. The right answer is \"" + entry.getValue() + "\".");
                log.add("Wrong. The right answer is \"" + entry.getValue() + "\".");
            }
            numberCards--;
            if (numberCards == 0) {
                break;
            }
        }
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void log() {
        System.out.println("File name:");
        log.add("File name:");
        Scanner fileName = new Scanner(System.in);
        String fileNameS = fileName.nextLine();
        log.add(fileNameS);
        log.add("The log has been saved.");

        File file = new File("/Users/ebelousov/IdeaProjects/Flashcards/Flashcards/task/" + fileNameS);
        BufferedWriter bf = null;
        try {
            bf = new BufferedWriter(new FileWriter(file));
            for(String str: log) {
                bf.write(str + System.lineSeparator());
            }
            bf.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("The log has been saved.");
    }

    public void hardestCard() {
        List<String> hardestCards = new ArrayList<>();
        int max = 1;
        for (String term : hardestCardsMap.keySet()) {
            Integer errors = hardestCardsMap.get(term);
            if (errors > max) {
                max = errors;
                hardestCards.clear();
                hardestCards.add(term);
            } else if (errors == max) {
                hardestCards.add(term);
            }
        }

        switch (hardestCards.size()) {
            case 0 -> {
                System.out.println("There are no cards with errors.");
                log.add("There are no cards with errors.");
            }
            case 1 ->
            {
                System.out.println("The hardest card is \"" + hardestCards.get(0) + "\". You have " + max + " errors answering it.");
                log.add("The hardest card is \"" + hardestCards.get(0) + "\". You have " + max + " errors answering it.");
            }
            default -> {
                StringBuilder terms = new StringBuilder();
                for (int i = 0; i < hardestCards.size(); i++) {
                    if (i < hardestCards.size() - 1) {
                        terms.append("\"").append(hardestCards.get(i)).append("\", ");
                    } else {
                        terms.append("\"").append(hardestCards.get(i)).append("\". ");
                    }
                }
                System.out.println("The hardest cards are " + terms + "You have " + max + " errors answering them.");
                log.add("The hardest cards are " + terms + "You have " + max + " errors answering them.");
            }
        }
    }

    public void resetStats() {
        for (Map.Entry<String, Integer> entry : hardestCardsMap.entrySet()) {
            String term = entry.getKey();
            hardestCardsMap.replace(term, 0);
        }
        System.out.println("Card statistics have been reset.");
        log.add("Card statistics have been reset.");
    }

    protected void exportFromCommandLine(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            List<String[]> exportedCards = new ArrayList<>();
            for (Map.Entry<String, String> entry : flashcards.entrySet()) {
                String term = entry.getKey();
                String definition = entry.getValue();
                exportedCards.add(new String[]{term, definition, Integer.toString(hardestCardsMap.get(term))});
            }
            oos.writeObject(exportedCards);
            System.out.println(flashcards.size() + " cards have been saved");
            log.add(flashcards.size() + " cards have been saved");
        } catch (IOException e) {
            System.out.println("File not found.");
            log.add("File not found.");
        }
    }

    protected void importFromCommandLine(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            List<String[]> importedCards = (ArrayList) ois.readObject();
            for (String[] card : importedCards) {
                flashcards.put(card[0], card[1]);
                hardestCardsMap.put(card[0], Integer.parseInt(card[2]));
            }
            System.out.println(importedCards.size() + " cards have been loaded");
            log.add(importedCards.size() + " cards have been loaded");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("File not found.");
            log.add("File not found.");
        }
    }

    public static void main(String[] args) {
        Main main = new Main();

        String exportFile = "";
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-import" -> main.importFromCommandLine(args[i + 1]);
                case "-export" -> exportFile = args[i + 1];
                default -> {
                }
            }
        }

        System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
        main.log.add("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");        Scanner scanner = new Scanner(System.in);
        String s;
        label:
        while (!"".equals(s = scanner.nextLine())) {
            switch (s) {
                case "add":
                    main.log.add(s);

                    main.addCard();
                    System.out.println("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    main.log.add("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    break;
                case "remove":
                    main.log.add(s);

                    main.removeCard();
                    System.out.println("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    main.log.add("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    break;
                case "import":
                    main.log.add(s);

                    main.importCards();
                    System.out.println("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    main.log.add("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    break;
                case "export":
                    main.log.add(s);

                    main.exportCards();
                    System.out.println("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    main.log.add("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    break;
                case "ask":
                    main.log.add(s);

                    main.ask();
                    System.out.println("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    main.log.add("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    break;
                case "exit":
                    main.log.add(s);

                    System.out.println("Bye bye!");
                    main.log.add("Bye bye!");
                    if (!"".equals(exportFile)) {
                        main.exportFromCommandLine(exportFile);
                    }
                    break label;
                case "log":
                    main.log.add(s);

                    main.log();
                    System.out.println("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    main.log.add("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    break;
                case "hardest card":
                case "hard":
                    main.log.add(s);

                    main.hardestCard();
                    System.out.println("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    main.log.add("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    break;
                case "reset stats":
                    main.log.add(s);

                    main.resetStats();
                    System.out.println("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    main.log.add("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                    break;
            }
        }
    }
}