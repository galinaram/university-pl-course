import org.junit.jupiter.api.Test;
import org.unecon.Task1;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Task1Test {

    @Test
    void testAllOddNumbers() {
        int[] input = {1, 3, 5, 7, 9};
        int[] expected = {1, 3, 5, 7, 9};
        int[] result = Task1.changeArray(input, input.length);

        assertArrayEquals(expected, result, "Все нечетные числа должны остаться на своих местах");
    }

    @Test
    void testAllEvenNumbers() {
        int[] input = {2, 4, 6, 8, 10};
        int[] expected = {2, 4, 6, 8, 10};
        int[] result = Task1.changeArray(input, input.length);

        assertArrayEquals(expected, result, "Все четные числа должны остаться на своих местах");
    }

    @Test
    void testMixedNumbers() {
        int[] input = {3, 6, 7, 9, 10, 15, 2, 8};
        int[] result = Task1.changeArray(input, input.length);

        // Проверяем, что все нечетные в начале
        boolean foundEven = false;
        for (int num : result) {
            if (num % 2 == 0) {
                foundEven = true;
            } else if (foundEven) {
                fail("Нечетное число найдено после четного: " + Arrays.toString(result));
            }
        }

        // Проверяем порядок нечетных чисел
        int[] expectedOdds = {3, 7, 9, 15};
        int oddIndex = 0;
        for (int num : result) {
            if (num % 2 != 0) {
                assertEquals(expectedOdds[oddIndex++], num, "Порядок нечетных чисел должен сохраниться");
            }
        }
    }

    @Test
    void testEmptyArray() {
        int[] input = {};
        int[] expected = {};
        int[] result = Task1.changeArray(input, input.length);

        assertArrayEquals(expected, result, "Пустой массив должен остаться пустым");
    }

    @Test
    void testSingleElementOdd() {
        int[] input = {7};
        int[] expected = {7};
        int[] result = Task1.changeArray(input, input.length);

        assertArrayEquals(expected, result, "Один нечетный элемент должен остаться без изменений");
    }

    @Test
    void testSingleElementEven() {
        int[] input = {4};
        int[] expected = {4};
        int[] result = Task1.changeArray(input, input.length);

        assertArrayEquals(expected, result, "Один четный элемент должен остаться без изменений");
    }

    @Test
    void testWithZeros() {
        int[] input = {0, 1, 2, 0, 3, 4};
        int[] result = Task1.changeArray(input, input.length);

        // Проверяем, что все нечетные в начале
        boolean foundEven = false;
        for (int num : result) {
            if (num % 2 == 0) {
                foundEven = true;
            } else if (foundEven) {
                fail("Нечетное число найдено после четного: " + Arrays.toString(result));
            }
        }

        // Проверяем порядок нечетных чисел
        int[] expectedOdds = {1, 3};
        int oddIndex = 0;
        for (int num : result) {
            if (num % 2 != 0) {
                assertEquals(expectedOdds[oddIndex++], num, "Порядок нечетных чисел должен сохраниться");
            }
        }
    }

    @Test
    void testNegativeNumbers() {
        int[] input = {-3, 6, -7, -9, 10, -15, 2, 8};
        int[] result = Task1.changeArray(input, input.length);

        // Проверяем, что все нечетные в начале
        boolean foundEven = false;
        for (int num : result) {
            if (num % 2 == 0) {
                foundEven = true;
            } else if (foundEven) {
                fail("Нечетное число найдено после четного: " + Arrays.toString(result));
            }
        }

        // Проверяем порядок нечетных чисел
        int[] expectedOdds = {-3, -7, -9, -15};
        int oddIndex = 0;
        for (int num : result) {
            if (num % 2 != 0) {
                assertEquals(expectedOdds[oddIndex++], num, "Порядок нечетных чисел должен сохраниться");
            }
        }
    }

    @Test
    void testAlreadySorted() {
        int[] input = {1, 3, 5, 2, 4, 6};
        int[] expected = {1, 3, 5, 2, 4, 6};
        int[] result = Task1.changeArray(input, input.length);

        assertArrayEquals(expected, result, "Уже отсортированный массив должен остаться без изменений");
    }
}