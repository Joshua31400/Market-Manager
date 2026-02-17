package utils;

public class Colors {
    // Reset
    public static final String RESET = "\033[0m";

    // Regular Colors
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String WHITE_BOLD = "\033[1;37m";

    // Background
    public static final String BLACK_BG = "\033[40m";
    public static final String RED_BG = "\033[41m";
    public static final String GREEN_BG = "\033[42m";
    public static final String YELLOW_BG = "\033[43m";
    public static final String BLUE_BG = "\033[44m";
    public static final String PURPLE_BG = "\033[45m";
    public static final String CYAN_BG = "\033[46m";
    public static final String WHITE_BG = "\033[47m";

    // Utility methods
    public static String colorize(String text, String color) {
        return color + text + RESET;
    }

    public static void setColor(String color) { System.out.print(color); }

    public static void resetColor() { System.out.print(RESET); }

    public static String success(String text) {
        return GREEN + text + RESET;
    }

    public static String error(String text) {
        return RED + text + RESET;
    }

    public static String warning(String text) {
        return YELLOW + text + RESET;
    }

    public static String primary(String text) {return WHITE_BOLD + text + RESET;}

    public static String secondary(String text) {return WHITE + text + RESET;}

    public static String data(String text) {return YELLOW_BOLD + text + RESET;}

    public static String inputLabel(String text) {return CYAN + text + RESET;}

}

