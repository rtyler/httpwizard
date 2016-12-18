
package io.lasagna.httpwizard

import spock.lang.Specification

class HttpWizardSpec extends Specification {
    def "ensure app can be instantiated"() {
        when:
        def app = new HttpWizard()

        then:
        app instanceof HttpWizard
    }
}
