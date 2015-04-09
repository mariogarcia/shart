package shart.app.util

import groovy.transform.CompileStatic

@CompileStatic
final class DateUtils {

    static final String FORMAT = 'yyyy-MM-dd'

    static String toRequest(Date date) {
        return date.format(FORMAT)
    }

}