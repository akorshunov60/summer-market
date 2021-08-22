# Урок . homeEx-3

## 1. С помощью АОП посчитайте по каждому сервису суммарное время,
##    уходящее на выполнение методов этих сервисов.
##    И по endpoint'у /statistic выдайте полученную статистику клиенту.

1. Создаем новый класс AOPService:

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

2. Создаем новый класс AOPController:

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistic")
public class AOPController {

    private final AOPService statistic;

    @GetMapping
    public Map<String, Long> getServiceStatistics() {
        return statistic.getServiceDurations();
    }
}

Результат работы сервиса AOPService по endpoint'у /statistic или через swagger-ui:
    
{
"ru.geekbrains.summer.services.ProductService": 27,
"ru.geekbrains.summer.services.UserService": 75,
"ru.geekbrains.summer.services.OrderService": 36
}