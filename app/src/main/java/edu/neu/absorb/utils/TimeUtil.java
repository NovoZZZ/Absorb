package edu.neu.absorb.utils;

public class TimeUtil {
    /**
     * convert counted seconds to time format, which is mm:ss
     *
     * @return converted time string
     */
    public static String convertSecondsToTime(int seconds) {
        StringBuilder result = new StringBuilder();
        if (seconds < 10) {
            result.append("00:0").append(seconds);
        } else if (seconds < 60) {
            result.append("00:").append(seconds);
        } else {
            int hours = seconds / 60;
            if (hours < 10) {
                result.append("0");
            }
            result.append(hours).append(":");
            int newSeconds = seconds % 60;
            if (newSeconds < 10) {
                result.append("0");
            }
            result.append(newSeconds);
        }
        return result.toString();
    }


    /**
     * convert seconds to xxH:yyM:zzS
     *
     * @param seconds duration
     * @return formatted duration
     */
    public static String convertSecondsToResultFormat(int seconds) {
        StringBuilder result = new StringBuilder();
        // hours
        if (seconds >= 60 * 60) {
            // if longer than 1 hour
            int hours = seconds / (60 * 60);
            result.append(hours).append("H:");
            seconds %= 60 * 60;
        }
        // minutes
        if (seconds >= 60) {
            // if longer than 1 minute
            int minutes = seconds / 60;
            result.append(minutes).append("M:");
            seconds %= 60;
        }
        result.append(seconds).append("S");
        return result.toString();
    }
}
