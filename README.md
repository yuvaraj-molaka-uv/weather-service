# weather-service
A Spring Boot-based weather service that provides the rest endpoints to 
1. Post the weather information: POST: /weather
2. Get the weather information by ID: GET: /weather/<id>
3. Get the weather information with the date (or) city details: GET: /weather

### Initial approaches followed to develop this service:
1. For DB CRUD operations, used ConcurrentHashMap
2. Used Lombok to automatically generating boilerplate code like getters, setters, constructors, equals, hashCode, and toString methods at compile time.
3. Used Lombok @Slf4j to implement Logging mechanism.
4.  