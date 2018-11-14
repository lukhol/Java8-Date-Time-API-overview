package com.lukhol.timeapi;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * Instant class - represent the start of a nanosecond on the timeline. This class is
 * useful for generating a time stamp to represent machine time.
 *
 * Instant timestamp = Instant.now();
 *
 * A value returned from the Instant class counts time beginning from the first second
 * of January 1, 1970 (1970-01-01T00:00:00Z) also called the <br>EPOCH</br>. An instant
 * that occurs before the epoch has a negative value, and an instant that occurs after
 * the epoch has a positive value.
 *
 * ================================= FROM ISO 8601 ==========================================
 * A single point in time can be represented by concatenating a complete date expression,
 * the letter T as a delimiter, and a valid time expression. For example "2007-04-05T14:30".
 *
 * [...]
 *
 * If the time is in UTC, add a Z directly after the time without a space. Z is the zone
 * designator for the zero UTC offset. "09:30 UTC" is therefore represented as "09:30Z" or
 * "0930Z". "14:45:15 UTC" would be "14:45:15Z" or "144515Z".
 * ==========================================================================================
 *
 *
 */
public class InstantClassTests {

    @Test
    public void localDateTime_toInstant_toString() {
        var localDateTime = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 30, 12, 123000000);
        assertThat(localDateTime.toInstant(ZoneOffset.of("+00:00")).toString())
                .isEqualTo("2018-01-01T12:30:12.123Z");

    }

    @Test
    public void instant_manipulatingMethods() {
        var now = Instant.now();
        var hourLater = now.plus(1, ChronoUnit.HOURS);
        var dayLater = now.plus(1, ChronoUnit.DAYS);

        assertThat(now.isAfter(now.minusMillis(1))).isTrue();
        assertThat(Instant.now().isAfter(now)).isTrue(); //Without line above does nor work!

        assertThat(now.until(hourLater, ChronoUnit.HOURS)).isEqualTo(1);
        assertThat(now.until(dayLater, ChronoUnit.SECONDS)).isEqualTo(24*60*60);
    }

    /*
     * The Instant class does not work with human units of time, such as years, months or days.
     * If you want to perform calculations in those units, you can convert an Instant to another
     * class, such as LocalDateTime or ZonedDateTime, by binding Instant with a time zone.
     *
     * Either a ZonedDateTime or an OffsetTimeZone object can be converted to an Instant object,
     * as each maps to an exact moment on the timeline. However, the reverse is not true. To convert
     * Instant object to a ZonedDateTime or an OffsetDateTime object requires supplying time zone,
     * or time zone offset information.
     */
    @Test
    public void toHumanFormat() {
        var nowTimestamp = Instant.now();
        var nowLocalDateTime = LocalDateTime.now();

        var localDateTime = LocalDateTime.ofInstant(nowTimestamp, ZoneId.systemDefault()); //Zone is required to convert from Instant

        assertThat(localDateTime.getDayOfMonth()).isEqualTo(nowLocalDateTime.getDayOfMonth());
        assertThat(localDateTime.getMinute()).isEqualTo(nowLocalDateTime.getMinute());
        assertThat(localDateTime.getSecond()).isEqualTo(nowLocalDateTime.getSecond());
    }
}