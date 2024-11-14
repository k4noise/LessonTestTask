package example.container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Тестирование работы контейнера
 *
 * @author k4noise
 * @since 09.11.2024
 */
class ContainerTest {
    /**
     * Контейнер
     */
    private Container container;

    /**
     * Создать контейнер для каждого теста
     */
    @BeforeEach
    void createContainer() {
        container = new Container();
    }

    /**
     * Проверка добавления разных элементов
     */
    @Test
    @DisplayName("Добавление элементов")
    void testAddItem() {
        Item firstItem = new Item(1);
        Item secondItem = new Item(2);

        container.add(firstItem);
        Assertions.assertEquals(1, container.size(), "Размер контейнера должен был увеличиться");
        Assertions.assertTrue(container.contains(firstItem), "Контейнер должен содержать элемент");

        Assertions.assertTrue(container.add(secondItem));
        Assertions.assertEquals(2, container.size(), "Размер контейнера должен был увеличиться");
        Assertions.assertTrue(container.contains(secondItem), "Контейнер должен содержать элемент");
    }

    /**
     * Проверка удаления существующего в контейнере элемента
     */
    @Test
    @DisplayName("Удаление существующего элемента")
    void testRemoveExistItem() {
        Item item = new Item(1);
        container.add(item);
        container.remove(item);
        container.contains(item);
        Assertions.assertEquals(0, container.size());
    }

    /**
     * Проверка удаления несуществующего в контейнере элемента
     */
    @Test
    @DisplayName("Удаление несуществующего элемента")
    void testRemoveNonExistItem() {
        Item item = new Item(1);
        container.add(new Item(2));
        container.remove(item);
        Assertions.assertEquals(1, container.size());
    }
}