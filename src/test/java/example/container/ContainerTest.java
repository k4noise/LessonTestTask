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
     * Проверка удаления элемента, добавленного в контейнер, по ссылке на тот же объект.
     */
    @Test
    @DisplayName("Удаление элемента по ссылке на тот же объект")
    void testRemoveItemBySameReference() {
        Item item = new Item(1);
        container.add(item);
        container.remove(item);
        Assertions.assertFalse(container.contains(item));
        Assertions.assertEquals(0, container.size());
    }

    /**
     * Проверка попытки удаления элемента с таким же значением, но другим объектом.
     * <p>Метод <code>remove</code> в <code>ArrayList</code>, согласно JavaDoc, удаляет первое вхождение элемента,
     * используя <code>Objects.equals(o, get(i))</code>. Поскольку <code>Item</code> не переопределяет
     * <code>equals</code> и <code>hashCode</code>, сравнение объектов выполняется по ссылке. <br>
     * Это означает, что два разных объекта с одинаковым значением не будут считаться равными.</p>
     */
    @Test
    @DisplayName("Попытка удаления элемента с таким же значением, но другим объектом")
    void testRemoveItemByDifferentObjectWithSameValue() {
        Item item = new Item(1);
        container.add(item);
        container.remove(new Item(1));
        Assertions.assertTrue(container.contains(item));
        Assertions.assertEquals(1, container.size());
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