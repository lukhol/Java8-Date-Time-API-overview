package com.lukhol.timeapi;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * A time zone is a region of the earth where the same standard time is used.
 * Each time zone is described by an identifier and usually has the format
 * region/city (Asia/Tokyo) and offset from Greenwich/UTC time. For example,
 * the offset for Tokyo is +09:00.
 *
 * The Date-Time Api provides two classes for specifying a time zone or an offset:
 * 1. ZoneId - specifies a time zone identifier and provides rules for converting
 *             between anInstant and a LocalDateTime
 * 2. ZoneOffset - specifies a time zone offset form Greenwich/UTC time
 *
 *
 * ZonedDateTime - handles a date and time with a corresponding timezone with a time zone
 *                 offset from Greenwich/UTC.
 *                 It's LocalDateTime and ZoneId
 *
 * OffsetDateTime - handles a date and time with a corresponding time zone offset from
 *                  Greenwich/UTC, without a timezone ID.
 *                  It's LocalDateTime and ZoneOffset
 *
 * OffsetTime - handles time with a corresponding zone offset from Greenwich/UTC, without
 *              time zone ID
 *              It's LocalTime and ZoneOffset.
 *
 *
 * When would you use OffsetDateTime instead of ZonedDateTime? For example when storing
 * time-stamps in a database that track only absolute offset from Greenwich/UTC time.
 * Or to transfer date-time by format like XML or JSON.
 *
 * Important is that ZonedDateTime use the ZoneRules from java.time.zone package. For example
 * most time zones experience a gap (typically of 1 hour) when moving the clock forward to daylight
 * saving time, and a time overlap when moving the clock back to standard time and the last hour
 * before the transition is repeated. The ZonedDateTime class accommodate this scenario, whereas
 * the OffsetDateTime and OffsetTime classes, which do not have accesss to the ZoneRule, do not.
 *
 * It is presented in test 'zonedDateTime_knowsAboutDaylightSavingTime()'
 */
public class TimeZoneAndOffsetClassesTests {

    @Test
    public void zonedDateTime_knowsAboutDaylightSavingTime() {
        var plZone = ZoneId.of("Poland");

        //LocalDateTime can be converted to a specific zone
        var ldtWinter = LocalDateTime.of(2018, Month.NOVEMBER, 14, 20, 30, 10);
        var zonedDateTimeWinter = ldtWinter.atZone(plZone);
        assertThat(zonedDateTimeWinter.getOffset().toString()).isEqualTo("+01:00"); //ZonedDateTime knows about daylight saving time.

        var ldtSummer = LocalDateTime.of(2018, Month.APRIL, 10, 20, 30, 11);
        var zonedDateTimeSummer = ldtSummer.atZone(plZone);
        assertThat(zonedDateTimeSummer.getOffset().toString()).isEqualTo("+02:00"); //ZonedDateTime knows about daylight saving time.
    }

    @Test
    public void zoneId() {
        var japanZone = ZoneId.of("Japan");
        var polandZone = ZoneId.of("Poland");

        var zonedInJapan = LocalDateTime.now().atZone(japanZone);
        var zonedInPoland = LocalDateTime.now().atZone(polandZone);

        assertThat(zonedInPoland.isEqual(zonedInJapan)).isFalse();
        assertThat(zonedInJapan.getOffset().toString()).isEqualTo("+09:00");

        //All zones:
        Set<String> allZoneIds = ZoneId.getAvailableZoneIds();

        //ZonedDateTime can be parsed of course
        ZonedDateTime.parse("2015-05-03T10:15:30+01:00[Europe/Paris]");
    }

    @Test
    public void offset() {
        var ldt = LocalDateTime.of(2015, Month.FEBRUARY, 26, 20, 30);
        assertThat(ldt.toString()).isEqualTo("2015-02-26T20:30");

        ZoneOffset offset = ZoneOffset.of("+02:00");
        var offsetDateTime = OffsetDateTime.of(ldt, offset);

        assertThat(offsetDateTime.getOffset().toString()).isEqualTo("+02:00");
        assertThat(offsetDateTime.getHour()).isEqualTo(20);
        assertThat(offsetDateTime.toString()).isEqualTo("2015-02-26T20:30+02:00");
    }
}