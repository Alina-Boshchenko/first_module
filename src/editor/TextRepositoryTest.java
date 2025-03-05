package editor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextRepositoryTest {

    private TextRepository repository = new TextRepository();
    private TextEditor.TextMemento m1 = new TextEditor.TextMemento(new char[10], 0, 10);
    private TextEditor.TextMemento m2 = new TextEditor.TextMemento(new char[20], 5, 20);

    @Test
    void pushPopShouldFollowLIFO() {
        repository.push(m1);
        repository.push(m2);
        assertSame(m2, repository.pop());
        assertSame(m1, repository.pop());
    }

    @Test
    void popEmptyShouldReturnNull() {
        assertNull(repository.pop());
    }
}