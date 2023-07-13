package ea.slartibartfast.walletservice.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisLockService implements LockService{
    private static final String MY_LOCK_KEY_PREFIX = "someLockKey::";
    private final LockRegistry lockRegistry;

    public boolean lock(String key){
        var lock = lockRegistry.obtain(MY_LOCK_KEY_PREFIX + key);
        return lock.tryLock();
    }

}

