package org.stuff;

import java.util.ArrayList;
import java.util.Arrays;

public class Menu {
    public String title;
    public String[] options;
    public Runnable[] actions;

    public Menu(String title, String[] options, Runnable[] actions) {
        this(title, options, actions, new Menu[0], null);
    }

    public Menu(String title, String[] options, Runnable[] actions, Menu parent) {
        this(title, options, actions, new Menu[0], parent);
    }

    public Menu(String title, String[] options, Runnable[] actions, Menu[] children) {
        this(title, options, actions, children, null);
    }

    public Menu(String title, String[] options, Runnable[] actions, Menu[] children, Menu parent) {
        ArrayList<String> optionsList = new ArrayList<>(Arrays.asList(options));
        ArrayList<Runnable> actionsList = new ArrayList<>(Arrays.asList(actions));
        int i = 0;
        for (Menu child : children) {
            optionsList.add(i, child.title);
            actionsList.add(i, () -> child.print().awaitInput());
            i++;
        }

        if (parent != null) {
            optionsList.add(0, "Back");
            actionsList.add(0, () -> parent.print().awaitInput());
        }

        options = optionsList.toArray(new String[0]);
        actions = actionsList.toArray(new Runnable[0]);

        if (options.length != actions.length)
            throw new IllegalArgumentException("Options and actions must be the same length.");

        this.title = title;
        this.options = options;
        this.actions = actions;
    }

    public Menu print() {
        Console.clear();

        System.out.println(ConsoleColor.WHITE_BOLD_BRIGHT + title);
        for (int i = 0; i < options.length; i++) {
            System.out.println(ConsoleColor.BLUE_BOLD + (i + 1) + ". " + ConsoleColor.WHITE_BRIGHT + options[i]);
        }

        return this;
    }

    /**
     * Waits for the user to input a valid option.
     *
     * @param defaultOption The default option **index** to select if the user inputs nothing. Must be a valid index, or -1 for no default.
     * @return The option selected.
     */
    public String awaitInput(int defaultOption) {
        String choice = Input.awaitInput(
                ConsoleColor.WHITE_BOLD_BRIGHT + "Enter your choice" +
                        (defaultOption >= 0 && defaultOption < options.length
                                ? ConsoleColor.WHITE + " (" + (defaultOption + 1) + ")" + ConsoleColor.WHITE_BOLD_BRIGHT
                                : "")
                        + ": ");

        if (choice.isBlank()) {
            String option = options[defaultOption];
            Runnable action = actions[defaultOption];
            Console.clear();
            action.run();
            return option;
        }

        int input;
        try {
            input = Integer.parseInt(choice) - 1;
        } catch (NumberFormatException e) {
            return awaitInput(defaultOption);
        }

        if (input >= 0 && input < options.length) {
            String option = options[input];
            Runnable action = actions[input];
            Console.clear();
            action.run();
            return option;
        }

        return awaitInput(defaultOption);
    }

    /**
     * Shorthand for awaitInput(-1);
     *
     * @return The option selected.
     */
    public String awaitInput() {
        return awaitInput(-1);
    }

    /**
     * Waits for the user to input a valid option.
     *
     * @param defaultOption The default option to select if the user inputs nothing. Must be a valid option, or "" for no default.
     * @return The option selected.
     */
    public String awaitInput(String defaultOption) {
        return awaitInput(Arrays.asList(options).indexOf(defaultOption));
    }
}
