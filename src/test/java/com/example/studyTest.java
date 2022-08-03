package com.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class studyTest {

    @Test
    void create() {
        Study study = new Study(100);
        assertNotNull(study);
        System.out.println("create");
    }

    @Test
    @Disabled
    void create1() {
        System.out.println("create1");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }

    @Test
    @DisplayName("스터디 만들기")
    @EnabledOnOs({ OS.MAC, OS.LINUX })
    public void create_new_study() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println("local");
        Study actual = new Study(100);
        assertThat(actual.getLimit()).isGreaterThan(0);

//        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
//            System.out.println("local");
//            Study actual = new Study(100);
//            assertThat(actual.getLimit()).isGreaterThan(0);
//        });
//
//        assumingThat("chanee".equalsIgnoreCase(test_env), () -> {
//            System.out.println("chanee");
//            Study actual = new Study(100);
//            assertThat(actual.getLimit()).isGreaterThan(0);
//        });

        Study study = new Study(-10);

//        assertAll(
//                () -> assertNotNull(study),
//                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
//                    () -> "스터디를 처음 만들면 " + StudyStatus.DRAFT + "상태다."),
//                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.")
//        );

//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
//        assertEquals("limit은 0보다 커야한다.", exception.getMessage());

        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });
    }
}