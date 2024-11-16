package example.bot;

import java.util.ArrayList;
import java.util.List;

/**
 * Фейк-бот для тестирования
 *
 * @author k4noise
 * @since 09.11.2024
 */
public class FakeBot implements Bot {
    /**
     * Отправленные сообщения
     */
    private final List<String> messages = new ArrayList<>();

    /**
     * Вернуть отправленное сообщение по его индексу с конца
     * <p>Метод предназначен для получения конкретного сообщения по индексу, начиная с последнего
     * из нескольких отправленных сообщений ботом на одно сообщение пользователя</p>
     */
    public String getMessageFromEnd(int index) {
        if (index < 0 || index >= messages.size()) {
            return null;
        }
        return messages.get(messages.size() - 1 - index);
    }

    /**
     * Вернуть последнее отправленное сообщение
     */
    public String getLastMessage() {
        return messages.getLast();
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        messages.add(message);
    }
}
