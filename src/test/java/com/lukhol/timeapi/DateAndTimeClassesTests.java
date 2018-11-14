package com.lukhol.timeapi;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoField;

import static org.assertj.core.api.Assertions.assertThat;

public class DateAndTimeClassesTests {


    /*
     * LocalTime deals with time only. Useful for representing human-based time of day,
     * such as movie time, or opening and closing time of the local library.
     * LocalTime does not store time zone or daylight saving time information.
     */
    @Test
    public void localTime() {
        var lt = LocalTime.parse("20:35:01.123");
        var lt2 = LocalTime.parse("20:35:01.124");
        assertThat(lt.getHour()).isEqualTo(20);
        assertThat(lt.getMinute()).isEqualTo(35);
        assertThat(lt.getSecond()).isEqualTo(1);

        //Milliseconds supported without getter
        assertThat(lt.isBefore(lt2)).isTrue();
        assertThat(lt.get(ChronoField.MILLI_OF_SECOND)).isEqualTo(123);
        assertThat(lt2.get(ChronoField.MILLI_OF_SECOND)).isEqualTo(124);

        //Fluent api for adding/subtracting time (remember: those classes are immutable!)
        assertThat(lt.plusSeconds(1).isAfter(lt2)).isTrue();

        //We can also check what is supported. DAY_OF_WEEK is not supported in time only related classes.
        assertThat(lt.isSupported(ChronoField.MILLI_OF_SECOND)).isTrue();
        assertThat(lt.isSupported(ChronoField.DAY_OF_WEEK)).isFalse();

        //Easy to set withSOMETHING
        assertThat(lt.withSecond(50).isAfter(lt)).isTrue();

        assertThat(lt.toString()).isEqualTo("20:35:01.123");
    }

    /*
     * LocalDateTime - handles both date and time, without a time zone.
     * This class is used to represent date(month-day-year) together with
     * time (hours-minute-second-nanosecond) and is, in effect combination
     * of LocalDate with LocalTime. This class can be used to represent for
     * eg. event (in local time). If timezone is needed use ZonedDateTime or
     * an OffsetDateTime instead.
     *
     * There are now() and various of...() creation method.
     */
    @Test
    public void localDateTime() {
        //No tests, usage only:
        var ldt1 = LocalDateTime.now();

        var ldt2 = LocalDateTime.of(1994, Month.FEBRUARY, 1, 10, 23, 14);
        var lt = LocalTime.of(ldt2.getHour(), ldt2.getMinute(), ldt2.getSecond());

        assertThat(lt.getHour()).isEqualTo(10);
        assertThat(lt.getMinute()).isEqualTo(23);
        assertThat(lt.getSecond()).isEqualTo(14);

        var ld = LocalDate.of(ldt2.getYear(), ldt2.getMonth(), ldt2.getDayOfMonth());
        assertThat(ld.getYear()).isEqualTo(1994);
        assertThat(ld.getMonth()).isEqualTo(Month.FEBRUARY);
        assertThat(ld.getDayOfMonth()).isEqualTo(1);

        //Fluent as joda
        var ldt3 = LocalDateTime.now().minusMinutes(5);
        assertThat(ldt1.isAfter(ldt3)).isTrue();
    }
}