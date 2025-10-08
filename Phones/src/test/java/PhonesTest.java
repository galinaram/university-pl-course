
import org.junit.jupiter.api.Test;
import ru.unecon.phones.Phones;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class PhonesTest {

    @Test
    public void testExampleFromAssignment() {
        assertEquals(
                List.of("9219243276", "2222222"),
                Phones.phoneNumbers(List.of("+7(921)924-3276", "222-22-22", "12-345-67"))
        );
    }

    @Test
    public void testValidTenDigitNumbers() {
        assertEquals(
                List.of("9219243276", "9111112233"),
                Phones.phoneNumbers(List.of("+7(921)924-3276", "8(911)1112233"))
        );
    }

    @Test
    public void testValidSevenDigitNumbers() {
        assertEquals(
                List.of("1234567", "1234567", "1234567"),
                Phones.phoneNumbers(List.of("1234567", "123-4567", "123-45-67"))
        );
    }

    @Test
    public void testInvalidNumbers() {
        assertEquals(List.of(), Phones.phoneNumbers(List.of("12-345-67")));
        assertEquals(List.of(), Phones.phoneNumbers(List.of("+7 (921) 9243276")));
    }

    @Test
    public void testMixedValidAndInvalid() {
        assertEquals(
                List.of("9111112233", "4444444"),
                Phones.phoneNumbers(List.of(
                        "8(911)1112233",
                        "444-44-44",
                        "12-345-67",
                        "89219243276"
                ))
        );
    }

    @Test
    public void testNullAndEmpty() {
        // Test 1: null list
        assertEquals(List.of(), Phones.phoneNumbers(null));

        // Test 2: empty list
        assertEquals(List.of(), Phones.phoneNumbers(List.of()));

        // Test 3: list with null elements - используем Arrays.asList который допускает null
        assertEquals(
                List.of("1234567"),
                Phones.phoneNumbers(Arrays.asList(null, "1234567", null))
        );

        // Test 4: list with empty strings
        assertEquals(
                List.of("1234567"),
                Phones.phoneNumbers(Arrays.asList("", "1234567", ""))
        );

        // Test 5: only empty strings and null
        assertEquals(
                List.of(),
                Phones.phoneNumbers(Arrays.asList("", null, ""))
        );

        // Test 6: создаем список через new ArrayList для полного контроля
        List<String> listWithNulls = new ArrayList<>();
        listWithNulls.add(null);
        listWithNulls.add("1234567");
        listWithNulls.add(null);
        assertEquals(
                List.of("1234567"),
                Phones.phoneNumbers(listWithNulls)
        );
    }
    @Test
    public void testAllValidFormats() {
        List<String> validNumbers = List.of(
                // 7-значные
                "1234567",      // без дефисов
                "123-4567",     // дефис после 3 цифр
                "123-45-67",    // дефисы после 3 и 5 цифр

                // 10-значные с +7
                "+7(123)4567890",
                "+7(123)456-7890",
                "+7(123)456-78-90",

                // 10-значные с 8
                "8(123)4567890",
                "8(123)456-7890",
                "8(123)456-78-90"
        );

        List<String> expected = List.of(
                "1234567", "1234567", "1234567",
                "1234567890", "1234567890", "1234567890",
                "1234567890", "1234567890", "1234567890"
        );

        assertEquals(expected, Phones.phoneNumbers(validNumbers));
    }
}