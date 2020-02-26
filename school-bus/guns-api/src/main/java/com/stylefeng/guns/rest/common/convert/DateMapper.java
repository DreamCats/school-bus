package com.stylefeng.guns.rest.common.convert;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateMapper {
 
    public String asString(Date date) {
        return date != null ? new SimpleDateFormat( "yyyy-MM-dd" )
            .format( date ) : null;
    }
 
    public Date asDate(String date) {
        try {
            return date != null ? new SimpleDateFormat( "yyyy-MM-dd" )
                .parse( date ) : null;
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
    }

    public Long asLong(Date date) {
        return date != null ? date.getTime() : null;
    }

    public Date asDate(Long date) {
        try {
            return date != null ? new Date(date) : null;
        }
        catch ( Exception e ) {
            throw new RuntimeException( e );
        }
    }
}