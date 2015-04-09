package shart.test

import spock.lang.Specification
import shart.dap.DataProvider

class BaseSpec extends Specification {

    DataProvider getDAP() {
        return new DataProvider()
    }

}
