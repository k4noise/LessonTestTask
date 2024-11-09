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
    private User user;

    /**
     * Создать для каждого теста пользователя, бота и логику бота
     */
    @BeforeEach
    void setUp() {
        bot = new FakeBot();
        botLogic = new BotLogic(bot);
        user = new User(1L);
    }

    /**
     * Проверка команды /test
     */
    @Test
    @DisplayName("Проверка режима тестирования")
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
        botLogic.processCommand(user, "100"); // Вычислите степень: 10^2, правильный ответ
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

        Assertions.assertEquals("Напоминание установлено", bot.getLastMessage());
        Thread.sleep(1024);
        Assertions.assertEquals("Сработало напоминание: 'Напоминание через секунду'", bot.getLastMessage());
    }
}