package com.boris1993.timesyncntp.utils;

import com.boris1993.timesyncntp.NtpConstants;

import java.util.Calendar;
import java.util.TimeZone;

public class NtpPacketUtils {
    private NtpPacketUtils() {
    }

    public static byte[] buildPacket() {
        final boolean[] booleans = new boolean[384];

        setLeapIndicator(booleans, NtpConstants.LeapIndicator.UNKNOWN_OR_NOT_SYNCHRONIZED);
        setVersionNumber(booleans, NtpConstants.VERSION);
        setMode(booleans, NtpConstants.Mode.CLIENT);
        setStratum(booleans, 16);
        setPoll(booleans, 0);
        setPrecision(booleans, 0);
        setRootDelay(booleans, 0);
        setRootDispersion(booleans, 0);
        setReferenceId(booleans, 0);
        setReferenceTimestamp(booleans, 0);
        setOriginTimestamp(booleans, 0);
        setReceiveTimestamp(booleans, 0);
        setTransmitTimestamp(booleans, 0);

        return HexUtils.booleanArrayToByteArray(booleans);
    }

    public static long getNtpTimestampMilliseconds(byte[] ntpTimestampBytes) {
        TimeZone utcZone = TimeZone.getTimeZone(TimeZone.getDefault().getID());
        Calendar calendar = Calendar.getInstance(utcZone);
        calendar.set(Calendar.YEAR, 1900);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        final long baseTimeMilliseconds = calendar.getTime().getTime();
        final int timezoneOffset = TimeZone.getDefault().getRawOffset();

        final String ntpHexString = HexUtils.bytesToHex(ntpTimestampBytes);
        final long ntpTimestamp =
                Long.parseLong(ntpHexString.substring(0, 8), 16) << 32 |
                        Long.parseLong(ntpHexString.substring(9), 16);

        final long seconds = (ntpTimestamp >>> 32) & 0xFFFFFFFFL;
        long fraction = ntpTimestamp & 0xFFFFFFFFL;
        fraction = new Double(1000D * fraction / 0x100000000L).longValue();

        return baseTimeMilliseconds + timezoneOffset + (seconds * 1000) + fraction;
    }

    private static void setLeapIndicator(final boolean[] booleans, final int leapIndicator) {
        setValuesInBooleanArray(booleans, leapIndicator, 0, 2);
    }

    private static void setVersionNumber(final boolean[] booleans, final int versionNumber) {
        setValuesInBooleanArray(booleans, versionNumber, 2, 5);
    }

    private static void setMode(final boolean[] booleans, final int mode) {
        setValuesInBooleanArray(booleans, mode, 5, 8);
    }

    private static void setStratum(final boolean[] booleans, final int stratum) {
        setValuesInBooleanArray(booleans, stratum, 8, 16);
    }

    private static void setPoll(final boolean[] booleans, final int poll) {
        setValuesInBooleanArray(booleans, poll, 16, 24);
    }

    private static void setPrecision(final boolean[] booleans, final int precision) {
        setValuesInBooleanArray(booleans, precision, 24, 32);
    }

    private static void setRootDelay(final boolean[] booleans, final int rootDelay) {
        setValuesInBooleanArray(booleans, rootDelay, 32, 64);
    }

    private static void setRootDispersion(final boolean[] booleans, final int rootDispersion) {
        setValuesInBooleanArray(booleans, rootDispersion, 64, 96);
    }

    private static void setReferenceId(final boolean[] booleans, final int refId) {
        setValuesInBooleanArray(booleans, refId, 96, 128);
    }

    private static void setReferenceTimestamp(final boolean[] booleans, final int referenceTimestamp) {
        setValuesInBooleanArray(booleans, referenceTimestamp, 128, 192);
    }

    private static void setOriginTimestamp(final boolean[] booleans, final int originTimestamp) {
        setValuesInBooleanArray(booleans, originTimestamp, 192, 256);
    }

    private static void setReceiveTimestamp(final boolean[] booleans, final int receiveTimestamp) {
        setValuesInBooleanArray(booleans, receiveTimestamp, 256, 320);
    }

    private static void setTransmitTimestamp(final boolean[] booleans, final int transmitTimestamp) {
        setValuesInBooleanArray(booleans, transmitTimestamp, 320, 384);
    }

    private static void setValuesInBooleanArray(
            final boolean[] booleans,
            final int value,
            final int beginIndex,
            final int endIndex) {

        final char[] binaries = HexUtils.padLeft(Integer.toBinaryString(value), endIndex - beginIndex).toCharArray();
        int currentIndexOfBinaries = 0;
        for (int i = beginIndex; i < endIndex; i++) {
            booleans[i] = binaries[currentIndexOfBinaries] == '1';
            currentIndexOfBinaries++;
        }
    }
}
