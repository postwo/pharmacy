package com.example.phproject.direction.service

import spock.lang.Specification
import spock.lang.Subject

class Base62ServiceTest extends Specification {

    @Subject
    private Base62Service base62Service

    def setup() {
        base62Service = new Base62Service()
    }

    def "check base 62 encoder/decoder"() { //인코딩한값을 다시 디코딩해서 값은 같은지 비교한다
        given:
        long num = 5

        when:
        String encodedId = base62Service.encodeDirectionId(num)

        long directionId = base62Service.decodeDirectionId(encodedId)

        then:
        num == directionId
    }
}