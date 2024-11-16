package example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Тестирование работы верхнеуровневой логики бота
 *
 * @author k4noise
 * @since 09.11.2024
 */
class BotLogicTest {
    /**
     * Фейк-бот
     */
    private FakeBot bot;
    /**
     * Верхнеуровневая логика бота
     */
    private BotLogic botLogic;
    /**
     * Пользователь
     */
    private final User user = new User(1L);

    /**
     * Создать для каждого теста бота и логику бота
     */
    @BeforeEach
    void setUp() {
        bot = new FakeBot();
        botLogic = new BotLogic(bot);
    }

    /**
     * Проверка команды /test, все ответы правильные
     */
    @Test
    @DisplayName("Проверка тестирования на правильных ответах")
    void testCommandTestAllRightAnswers() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals("Вычислите степень: 10^2", bot.getLastMessage());
        botLogic.processCommand(user, "100"); // Правильный ответ
        Assertions.assertEquals("Правильный ответ!", bot.getMessageFromEnd(1));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());
        botLogic.processCommand(user, "6"); // Правильный ответ
        Assertions.assertEquals("Правильный ответ!", bot.getMessageFromEnd(1));

        Assertions.assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * Проверка команды /test, все ответы неправильные
     */
    @Test
    @DisplayName("Проверка тестирования на неправильных ответах")
    void testCommandTestAllWrongAnswers() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals("Вычислите степень: 10^2", bot.getLastMessage());
        botLogic.processCommand(user, "10"); // Неправильный ответ
        Assertions.assertEquals("Вы ошиблись, верный ответ: 100", bot.getMessageFromEnd(1));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());
        botLogic.processCommand(user, "2"); // Неправильный ответ
        Assertions.assertEquals("Вы ошиблись, верный ответ: 6", bot.getMessageFromEnd(1));

        Assertions.assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * Проверка команды /test, один правильный, один неправильный ответ
     */
    @Test
    @DisplayName("Проверка тестирования на разных ответах")
    void testCommandTest() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals("Вычислите степень: 10^2", bot.getLastMessage());
        botLogic.processCommand(user, "100"); // Правильный ответ
        Assertions.assertEquals("Правильный ответ!", bot.getMessageFromEnd(1));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());
        botLogic.processCommand(user, "2"); // Неправильный ответ
        Assertions.assertEquals("Вы ошиблись, верный ответ: 6", bot.getMessageFromEnd(1));

        Assertions.assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * Проверка команды /repeat
     */
    @Test
    @DisplayName("Проверка режима повтора вопросов с неправильным ответом")
    void testCommandRepeat() {
        botLogic.processCommand(user, "/test");
        Assertions.assertEquals("Вычислите степень: 10^2", bot.getLastMessage());
        botLogic.processCommand(user, "100"); // Вычислите степень: 10^2, правильный ответ
        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());
        botLogic.processCommand(user, "2"); // Сколько будет 2 + 2 * 2, неправильный ответ

        botLogic.processCommand(user, "/repeat");

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());
        botLogic.processCommand(user, "6"); // Правильный ответ
        Assertions.assertEquals("Правильный ответ!", bot.getMessageFromEnd(1));

        Assertions.assertEquals("Тест завершен", bot.getLastMessage());

        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Нет вопросов для повторения", bot.getLastMessage());
    }

    /**
     * Проверка команды /notify
     */
    @Test
    @DisplayName("Проверка команды напоминания")
    void testCommandNotify() throws InterruptedException {
        botLogic.processCommand(user, "/notify");

        Assertions.assertEquals("Введите текст напоминания", bot.getLastMessage());
        botLogic.processCommand(user, "Напоминание через секунду");

        Assertions.assertEquals("Через сколько секунд напомнить?", bot.getLastMessage());
        botLogic.processCommand(user, "1");
        Assertions.assertEquals("Напоминание установлено", bot.getLastMessage());

        Thread.sleep(100);
        Assertions.assertEquals("Напоминание установлено", bot.getLastMessage());
        Thread.sleep(1024);
        Assertions.assertEquals("Сработало напоминание: 'Напоминание через секунду'", bot.getLastMessage());
    }
}