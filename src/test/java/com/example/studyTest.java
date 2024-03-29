package com.example;

import com.example.domain.OldStudy;
import com.example.domain.StudyStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

//@ExtendWith(FindSlowTestExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.class)
class studyTest {

    int value = 1;

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

    @Order(2)
    @FastTest @Tag("fast")
    @DisplayName("스터디 만들기 fast")
    @EnabledOnOs({OS.MAC, OS.LINUX})
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11})
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void create_new_study() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);
        assertTrue("LOCAL".equalsIgnoreCase(test_env));

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            System.out.println("local");
            OldStudy actual = new OldStudy(100);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

        assumingThat("chanhee".equalsIgnoreCase(test_env), () -> {
            System.out.println("chanhee");
            OldStudy actual = new OldStudy(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

        System.out.println(this);
        System.out.println(value++);
        OldStudy actual = new OldStudy(1);
        assertThat(actual.getLimit()).isGreaterThan(0);

        assertTimeout(Duration.ofMillis(100), () -> {
            new OldStudy(10);
            Thread.sleep(300);
        });

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new OldStudy(-10));

        String message = exception.getMessage();
        assertEquals("limit은 0보다 커야한다.", message);

        OldStudy study = new OldStudy(100);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디를 처음 만들면 " + StudyStatus.DRAFT + " 상태다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.")
        );
    }

    @Order(1)
    @SlowTest
    @DisplayName("스터디 만들기 slow")
    @DisabledOnOs(OS.MAC)
    @EnabledOnJre(JRE.OTHER)
    void create_new_study_again() {
        System.out.println(this);
        System.out.println("create1 " + value++);
    }

    @Order(3)
    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("test " + repetitionInfo.getCurrentRepetition() + "/" +
                repetitionInfo.getTotalRepetitions());
    }

    @Order(4)
    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
//    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요."})
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest(
//            @ConvertWith(StudyConverter.class) Study study
//            ArgumentsAccessor argumentsAccessor
            @AggregateWith(StudyAggregator.class) OldStudy study) {
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new OldStudy(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(OldStudy.class, targetType, "Can only convert");
            return new OldStudy(Integer.parseInt(source.toString()));
        }
    }

    @BeforeAll
    void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    void afterAll() {
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
}