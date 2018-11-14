package com.lukhol.timeapi;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * Date-Time API provide parse method for parsing a string contains date and time information.
 * These classes also provide format methods for formatting temporal-based objects for display.
 * In both cases, the process is similar: you provide a pattern to the DateTimeFormatter to create
 * a formatter object. This formatter is then passed to the parse or format method.
 *
 * The parse and format methods throw an exception if a problem occurs during the conversion process.
 * DatetimeParseException/DateTimeException could be thrown.
 *
 * DateTimeFormatter class is both immutable and thread-sage - it can (and should) be assigned to
 * a static constant where appropriate.
 */
public class ParsingAndFormattingTests {

    @Test
    public void predefinedFormats() {
        var ldt = LocalDateTime.of(2018, Month.JANUARY, 20, 10, 30, 40);

        assertThat(ldt.format(DateTimeFormatter.ISO_LOCAL_DATE)).isEqualTo("2018-01-20");
        assertThat(ldt.format(DateTimeFormatter.ISO_LOCAL_TIME)).isEqualTo("10:30:40");
        //more there: https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#predefined
    }

    @Test //https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#patterns
    public void customParsePatterns() {
        var formatter = DateTimeFormatter.ofPattern("yyyy<>MM<>dd"); //Patter should be static (is thread safety and immutable)
        var localDate = LocalDate.parse("2018<>10<>01", formatter);
        assertThat(localDate.getYear()).isEqualTo(2018);
        assertThat(localDate.getMonth()).isEqualTo(Month.OCTOBER);
        assertThat(localDate.getDayOfMonth()).isEqualTo(1);

        var formatterTwo = DateTimeFormatter.ofPattern("dd--MM--yyyy HH--mm");
        var localDateTime = LocalDateTime.parse("05--12--2018 23--05", formatterTwo);
        assertThat(localDateTime.getYear()).isEqualTo(2018);
        assertThat(localDateTime.getMonth()).isEqualTo(Month.DECEMBER);
        assertThat(localDateTime.getDayOfMonth()).isEqualTo(5);
    }

    @Test
    public void customFormatPatterns() {
        var ldt = LocalDateTime.of(2018, Month.DECEMBER, 20, 22, 30, 40);

        var format = DateTimeFormatter.ofPattern("MM d yyyy hh:mm a");
        var out = ldt.format(format);

        assertThat(out).isEqualTo("12 20 2018 10:30 PM");
    }
}