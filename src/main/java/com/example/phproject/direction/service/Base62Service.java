package com.example.phproject.direction.service;

import io.seruco.encoding.base62.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Base62Service {

    // https://github.com/seruco/base62
    private static final Base62 base62Instance = Base62.createInstance();

    public String encodeDirectionId(Long directionId) { //인코딩은 암호화
        return new String(base62Instance.encode(String.valueOf(directionId).getBytes()));
    }

    public Long decodeDirectionId(String encodedDirectionId) { //인코딩된값을 다시 원래상태로 되돌린다

        String resultDirectionId = new String(base62Instance.decode(encodedDirectionId.getBytes()));
        return Long.valueOf(resultDirectionId);
    }
}
