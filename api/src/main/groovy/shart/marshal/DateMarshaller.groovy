package shart.marshal

import shart.marshal.Marshaller
import org.springframework.stereotype.Component

@Component
class DateMarshaller extends Marshaller<Date> {

    DateMarshaller() {
        super(Date)
    }

    String marshal(Date date) {
        return date.format('yyyy-MM-dd')
    }

}
