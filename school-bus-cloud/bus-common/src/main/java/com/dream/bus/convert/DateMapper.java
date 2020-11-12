/**
 * @program school-bus-cloud
 * @description: DateMapper
 * @author: mf
 * @create: 2020/10/31 15:36
 */

package com.dream.bus.convert;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateMapper{

    public String asString(LocalDateTime dateTime) {
        return dateTime != null ? DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss")
                .format(dateTime) : null;
    }

//    public LocalDateTime asLocalDateTime(String date) {
//        try {
//            return date != null ? LocalDateTime
//                    .parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
//
//        }
//        catch ( Exception e ) {
//            throw new RuntimeException( e );
//        }
//    }

//    public String asString(Date date) {
//        return date != null ? new SimpleDateFormat( "yyyy-MM-dd" )
//            .format( date ) : null;
//    }
//
//    public Date asDate(String date) {
//        try {
//            return date != null ? new SimpleDateFormat( "yyyy-MM-dd" )
//                .parse( date ) : null;
//        }
//        catch ( ParseException e ) {
//            throw new RuntimeException( e );
//        }
//    }

//    public Long asLong(Date date) {
//        return date != null ? date.getTime() : null;
//    }
//
//    public Date asDate(Long date) {
//        try {
//            return date != null ? new Date(date) : null;
//        }
//        catch ( Exception e ) {
//            throw new RuntimeException( e );
//        }
//    }
}
