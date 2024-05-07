plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.trial"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Framework Dependencies
    implementation("org.springframework.boot:spring-boot-starter-web")                   // Web functionalities
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")             // Spring Data JPA
    implementation("org.springframework.boot:spring-boot-starter-validation")           // Validation support
    implementation("org.springframework.boot:spring-boot-starter-aop")                  // Aspect-Oriented Programming including AspectJ

    // Database and Persistence
    runtimeOnly("org.postgresql:postgresql")                                           // PostgreSQL JDBC Driver
    implementation("org.liquibase:liquibase-core")                                     // Database migration tool

    // Caching
    implementation("org.hibernate:hibernate-core:6.5.0.Final")                         // Hibernate ORM
    implementation("org.infinispan:infinispan-hibernate-cache-v60:14.0.28.Final")      // Infinispan cache provider
    testImplementation("org.infinispan:infinispan-jcache:15.0.2.Final")                // Infinispan JCache support for testing

    // API Documentation
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")          // OpenAPI 3 documentation

    // Code Generation and Management
    compileOnly("org.projectlombok:lombok")                                            // Lombok to reduce boilerplate code
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")                              // MapStruct for object mapping
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    // Testing Dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")            // Core testing starter, including JUnit and Mockito
    testImplementation("org.junit.jupiter:junit-jupiter-api")                          // JUnit Jupiter API for tests
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")                          // JUnit Jupiter Engine
    testImplementation("org.mockito:mockito-core")                                     // Mockito for mocking
    testImplementation("org.mockito:mockito-junit-jupiter")                            // Mockito integration with JUnit
    testImplementation("org.hamcrest:hamcrest:2.2")                                    // Hamcrest for assertions
}


tasks.withType<Test> {
    useJUnitPlatform()
}
