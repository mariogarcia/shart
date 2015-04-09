package shart.marshal

import javax.annotation.PostConstruct
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import groovy.util.logging.Slf4j

@Slf4j
@Component
class MarshallerLoader {

    @Autowired
    List<Marshaller> marshallerList

    @PostConstruct
    void init() {
        log.debug('Registering marshallers: ${marshallerList*.clazzToRegister}')
        marshallerList*.register()
    }

}
