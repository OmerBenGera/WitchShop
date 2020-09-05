package me.jwhz.witchshop.shop;

public enum Time {

    MINUTE("min", 60000L),
    HOUR("h", 3600000L),
    DAY("d", 86400000L),
    WEEK("w", 604800000),
    MONTH("m", 2592000000L);

    private long length;
    public String init;

    Time(String init, long length) {

        this.init = init;
        this.length = length;

    }

    public long getLength() {

        return length;

    }

    public static long getTimeToAdd(String string) {

        Time time = null;

        for (Time t : Time.values())
            if (string.contains(t.init)) {

                if (string.contains("min")) {

                    time = MINUTE;

                }

                time = t;

            }

        if (time == null)
            return 0;

        double multiplier = Double.parseDouble(string.replace(time.init, ""));

        return (long) (time.getLength() * multiplier);

    }

}
