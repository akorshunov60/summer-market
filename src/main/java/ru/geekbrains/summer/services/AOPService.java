package ru.geekbrains.summer.services;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, реализующий сквозную функциональность, в данном случае мониторинг
 * суммарного времени, уходящего на выполнение методов сервисов из пакета
 * ru.geekbrains.summer.services
 */

@RequiredArgsConstructor
@Aspect
@Service
public class AOPService {

    private final Map<String, Long> serviceDurations = new HashMap<>();

    @Around("execution(public * ru.geekbrains.summer.services.*Service.*(..))")
    public Object methodProfiling(ProceedingJoinPoint pjp) throws Throwable {

        Instant start = Instant.now();
        Object out = pjp.proceed();
        Instant finish = Instant.now();

        String serviceName = pjp.getSignature().getDeclaringTypeName();
        long duration = Duration.between(start, finish).toMillis();
        addToMap(serviceName, duration);
        return out;
    }

    private void addToMap(String service, Long duration) {
        long totalDuration = serviceDurations.getOrDefault(service, 0L) + duration;
        serviceDurations.put(service, totalDuration);
        System.out.println(serviceDurations);
    }

    public Map<String, Long> getServiceDurations() {
        return new HashMap<>(serviceDurations);
    }
}
