# Урок 7. homeEx-7

# 1. Покрыть Summer Market тестами

1. Созданы следующие классы для тестирования:
   
    + UserServiceTest (Process finished with exit code 0)
    + ProductServiceTest (Process finished with exit code 0)
    + RepositoriesTest (Process finished with exit code 0)
      
    - CartTest (org.springframework.data.redis.RedisConnectionFailureException:
               Cannot get Jedis connection; nested exception is redis.clients.jedis.exceptions.
               JedisConnectionException: Could not get a resource from the pool)
      
    + JsonTests (Process finished with exit code 0)
    + CategoryControllerTest (Process finished with exit code 0)
    + SpyTest (Process finished with exit code 0)
      
    - SecurityTest (passed:3 of 4 tests;
                    Не проходит securityTokenTest(): java.lang.AssertionError: Status expected:<200> but was:<404>
                    Expected :200
                    Actual   :404) 
      
    - FullServerRunTest (org.springframework.web.client.RestClientException:
                        Error while extracting response for type [interface java.util.List] and content type [application/json];
                        nested exception is org.springframework.http.converter.HttpMessageNotReadableException:
                        JSON parse error: Cannot deserialize value of type `java.util.ArrayList<java.lang.Object>`
                        from Object value (token `JsonToken.START_OBJECT`);
                        nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException:
                        Cannot deserialize value of type `java.util.ArrayList<java.lang.Object>`
                        from Object value (token `JsonToken.START_OBJECT`)
                        at [Source: (PushbackInputStream); line: 1, column: 1])