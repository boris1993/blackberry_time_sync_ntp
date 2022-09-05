package com.boris1993.timesyncntp;

public final class NtpConstants {
    private NtpConstants() {
    }

    public static final int NTP_V4_PACKET_SIZE = 384;

    /**
     * 2-bit integer warning of an impending leap second
     * to be inserted or deleted in the last minute of the current month.
     */
    public static class LeapIndicator {
        public static final int NO_WARNING = 0;
        public static final int LAST_MINUTE_OF_THE_DAY_HAS_61_SECONDS = 1;
        public static final int LAST_MINUTE_OF_THE_DAY_HAS_59_SECONDS = 2;
        public static final int UNKNOWN_OR_NOT_SYNCHRONIZED = 3;
    }

    /**
     *  3-bit integer representing the NTP version number, currently 4.
     */
    public static final int VERSION = 3;

    /**
     * 3-bit integer representing the mode
     */
    public static class Mode {
        public static final int RESERVED = 0;
        public static final int SYMMETRIC_ACTIVE = 1;
        public static final int SYMMETRIC_PASSIVE = 2;
        public static final int CLIENT = 3;
        public static final int SERVER = 4;
        public static final int BROADCAST = 5;
        public static final int NTP_CONTROL_MESSAGE = 6;
    }

    /**
     * 8-bit integer representing the stratum
     */
    public static class Stratum {
        public static final int UNSPECIFIED_OR_INVALID = 0;
        public static final int PRIMARY_SERVER = 1;
        public static final int UNSYNCHRONIZED = 6;
    }
}
