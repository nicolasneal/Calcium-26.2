package net.nicolas.calcium.state;

public final class CakeEatingState {

    private static boolean inProgress = false;

    private CakeEatingState() {}

    public static boolean isInProgress() {
        return inProgress;
    }

    public static void begin() {
        inProgress = true;
    }

    public static void end() {
        inProgress = false;
    }

}
