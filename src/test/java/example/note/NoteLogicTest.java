package example.note;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Тестирование логики работы с заметками
 *
 * @author k4noise
 * @since 08.11.2024
 */
class NoteLogicTest {
    /**
     * Обработчик сообщений для работы с заметками
     */
    private NoteLogic noteLogic;

    /**
     * Создать обработчик сообщений для каждого теста
     */
    @BeforeEach
    void createNoteLogic() {
        noteLogic = new NoteLogic();
    }

    /**
     * Проверка добавления заметки и ее отображения
     */
    @Test
    @DisplayName("Добавление и отображение заметки")
    void testHandleMessageAddShowNote() {
        String addNoteMessage = noteLogic.handleMessage("/add New Note");
        String allNotes = noteLogic.handleMessage("/notes");

        Assertions.assertEquals("Note added!", addNoteMessage, "Должно быть сообщение об успешном добавлении заметки");
        Assertions.assertEquals("""
                        Your notes:
                        1. New Note""" , allNotes, "Добавленная заметка должна отображаться"
        );
    }

    /**
     * Проверка редактирования существующей заметки
     */
    @Test
    @DisplayName("Редактирование существующей заметки")
    void testHandleMessageEditExistNote() {
        noteLogic.handleMessage("/add New Note");
        String editNoteMessage = noteLogic.handleMessage("/edit 1 Edited Note");
        String allNotes = noteLogic.handleMessage("/notes");

        Assertions.assertEquals("Note edited!", editNoteMessage, "Должно быть сообщение о успешном изменении заметки");
        Assertions.assertEquals("""
                Your notes:
                1. Edited Note""", allNotes, "Заметка должна была измениться"
        );
    }

    /**
     * Проверка редактирования несуществующей заметки
     */
    @Test
    @DisplayName("Редактирование несуществующей заметки")
    void testHandleMessageEditNonExistNote() {
        String editNoteMessage = noteLogic.handleMessage("/edit 1 Edited Note");
        Assertions.assertEquals(
                "Can't edit note with id 1 - note don't exists",
                editNoteMessage, "Должно быть сообщение об ошибке редактирования"
        );
    }

    /**
     * Проверка удаления существующей заметки
     */
    @Test
    @DisplayName("Удаление существующей заметки")
    void testHandleMessageDeleteExistNote() {
        noteLogic.handleMessage("/add New Note");
        noteLogic.handleMessage("/add Another Note");
        String removeNoteMessage = noteLogic.handleMessage("/del 1");
        String allNotes = noteLogic.handleMessage("/notes");

        Assertions.assertEquals("Note deleted!", removeNoteMessage, "Должно быть сообщение о успешном удалении заметки");
        Assertions.assertEquals("""
                Your notes:
                1. Another Note""", allNotes, "Заметка New Note должна была удалиться"
        );
    }


    /**
     * Проверка удаления несуществующей заметки
     */
    @Test
    @DisplayName("Удаление несуществующей заметки")
    void testHandleMessageDeleteNonExistNote() {
        String removeNoteMessage = noteLogic.handleMessage("/del 1");
        Assertions.assertEquals(
                "Can't delete note with id 1 - note don't exists",
                removeNoteMessage, "Должно быть сообщение об ошибке удаления"
        );
    }
}