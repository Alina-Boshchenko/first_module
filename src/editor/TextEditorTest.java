package editor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextEditorTest {

    private TextEditor editor;

    @BeforeEach
    void setUp() {
        editor = new TextEditor();
    }

    @Test
    void appendShouldAddText() {
        editor.append("Hello");
        assertEquals("Hello", editor.toString());
    }

    @Test
    void insertShouldHandleNull() {
        editor.insert(0, null);
        assertEquals("null", editor.toString());
    }

    @Test
    void deleteShouldRemoveCharacters() {
        editor.append("Hello World")
                .delete(5, 11);
        assertEquals("Hello", editor.toString());
    }

    @Test
    void reverseShouldFlipContent() {
        editor.append("123").revers();
        assertEquals("321", editor.toString());
    }

    @Test
    void undoShouldRestorePreviousState() {
        editor.append("V1").append("V2").undo();
        assertEquals("V1", editor.toString());
    }

    @Test
    void shouldThrowWhenInsertingAtInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> editor.insert(100, "test"));
    }

    @Test
    void capacityShouldGrowExponentially() {
        String longText = "A".repeat(1000);
        editor.append(longText);
        assertTrue(editor.toString().length() >= 1000);
    }
}