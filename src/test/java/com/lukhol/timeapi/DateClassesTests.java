package com.lukhol.timeapi;

//java.time //Standard ISO calendar system
//java.time.chrono //Non standard ISO calendar system
//java.time.format //Formatting ang parsing data
//java.time.temporal //
//java.time.zone //Classes that support time zones

//There are two types of representing time. Human time and machine time.
//Machine use elapsed nanoseconds from an origin, called epoch to represent time.

//Instant - proper for timestamp.

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

//https://docs.oracle.com/javase/tutorial/datetime/iso/overview.html
public class DateClassesTests {

    @Test
    public void dayOfWeekEnums() {
        assertThat(DayOfWeek.MONDAY.plus(2)).isEqualTo(DayOfWeek.WEDNESDAY);
        assertThat(DayOfWeek.of(7)).isEqualTo(DayOfWeek.SUNDAY);
        assertThat(DayOfWeek.valueOf("MONDAY")).isEqualTo(DayOfWeek.MONDAY);
        assertThat(DayOfWeek.values()).hasSize(7);
        //assertThat(DayOfWeek.from(TemporalAccessor))

        //Print day of week - in polish
        assertThat(DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"))).isEqualTo("poniedziałek");
        assertThat(DayOfWeek.MONDAY.getDisplayName(TextStyle.NARROW, Locale.forLanguageTag("PL"))).isEqualTo("P");
        assertThat(DayOfWeek.MONDAY.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("pl"))).isEqualTo("pon.");

        //Print day of week - in english starts with big letter
        assertThat(DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("en"))).isEqualTo("Monday");
        assertThat(DayOfWeek.MONDAY.getDisplayName(TextStyle.NARROW, Locale.forLanguageTag("en"))).isEqualTo("M");
        assertThat(DayOfWeek.MONDAY.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("en"))).isEqualTo("Mon");
    }

    @Test
    public void monthEnum() {
        assertThat(Month.JANUARY.getValue()).isEqualTo(1);
        assertThat(Month.DECEMBER.getValue()).isEqualTo(12);
        assertThat(Month.values()).hasSize(12);

        assertThat(Month.FEBRUARY.maxLength()).isEqualTo(29);
        assertThat(Month.FEBRUARY.minLength()).isEqualTo(28);

        Month month = Month.AUGUST;
        Locale locale = Locale.ENGLISH;
        assertThat(month.getDisplayName(TextStyle.FULL, locale)).isEqualTo("August");
        assertThat(month.getDisplayName(TextStyle.NARROW, locale)).isEqualTo("A");
        assertThat(month.getDisplayName(TextStyle.SHORT, locale)).isEqualTo("Aug");

        locale = Locale.forLanguageTag("pl");
        assertThat(month.getDisplayName(TextStyle.FULL, locale)).isEqualTo("sierpnia");
        assertThat(month.getDisplayName(TextStyle.NARROW, locale)).isEqualTo("s");
        assertThat(month.getDisplayName(TextStyle.SHORT, locale)).isEqualTo("sie");
    }

    //There are 4 classes to deal with date information, without respect to time or time zone.
    //LocalDate, YearMont, MontDay and Year
    @Test
    public void date() {
        var date1 = LocalDate.of(2020, Month.JANUARY, 20);
        var date2 = LocalDate.of(2015, Month.AUGUST, 15);
        assertThat(date1.isAfter(date2)).isTrue();
        assertThat(date2.isBefore(date1)).isTrue();
        assertThat(date1.getDayOfMonth()).isEqualTo(20);
        assertThat(date1.getMonth()).isEqualTo(Month.JANUARY);
        assertThat(date1.getDayOfMonth()).isEqualTo(20);

        //Using TemporaAdjusters we can get for example next Wednesday from today.
        var nextWed = date1.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        var now = LocalDate.now();
        assertThat(nextWed.isAfter(now)).isTrue();

        //Usage of with (with TemporalAdjusters)
        var firstMon = LocalDate.of(2000, Month.JANUARY, 3);
        assertThat(firstMon.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);

        var adju = TemporalAdjusters.next(DayOfWeek.MONDAY);
        var nextMon = firstMon.with(adju);

        assertThat(firstMon.plusDays(7).compareTo(nextMon)).isEqualTo(0);
        assertThat(firstMon.plusDays(7).isEqual(nextMon)).isTrue();
    }

    @Test
    public void yearMont() {
        //Leap year
        var february2012 = YearMonth.of(2012, Month.FEBRUARY);

        //Not leap year
        var february2014 = YearMonth.of(2014, Month.FEBRUARY);

        assertThat(february2012.lengthOfYear()).isEqualTo(366);
        assertThat(february2012.lengthOfMonth()).isEqualTo(29);
        assertThat(february2012.isLeapYear()).isTrue();

        assertThat(february2014.lengthOfYear()).isEqualTo(365);
        assertThat(february2014.lengthOfMonth()).isEqualTo(28);
        assertThat(february2014.isLeapYear()).isFalse();
    }

    @Test
    public void monthDay() {
        var monthDay = MonthDay.of(Month.FEBRUARY, 29); //Only in leap year

        assertThat(monthDay.isValidYear(2012)).isTrue();
        assertThat(monthDay.isValidYear(2013)).isFalse();
        assertThat(monthDay.isValidYear(2014)).isFalse();
        assertThat(monthDay.isValidYear(2015)).isFalse();
        assertThat(monthDay.isValidYear(2016)).isTrue();
    }

    @Test
    public void year() {
        assertThat(Year.of(2012).isLeap()).isTrue();
        assertThat(Year.of(2013).isLeap()).isFalse();
        assertThat(Year.of(2014).isLeap()).isFalse();
        assertThat(Year.of(2015).isLeap()).isFalse();
        assertThat(Year.of(2016).isLeap()).isTrue();
    }
}