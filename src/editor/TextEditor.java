package editor;

import java.util.Arrays;

public class TextEditor {

    private char[] text;
    private int len;
    private int capacity;
    private TextRepository repository;

    {
        repository = new TextRepository();
        len = 0;
    }

    public TextEditor() {
        capacity = 16;
        text = new char[capacity];
        saveMemento();
    }

    public TextEditor(int capacity) {
        this.capacity = capacity;
        text = new char[capacity];
        saveMemento();
    }

    public TextEditor append(String text) {
        saveMemento();
        if (text == null) {
            convertNull();
            return this;
        }
        increaseCapacity(text.length());
        text.getChars(0, text.length(), this.text, len);
        len += text.length();
        return this;
    }

    public TextEditor insert(int index, String text) {
        if (index < 0 || index > len) throw new IndexOutOfBoundsException("Index out of bounds");
        saveMemento();
        if (text == null) {
            text = "null";
        }
        increaseCapacity(text.length());
        System.arraycopy(this.text, index, this.text, index + text.length(), len - index);
        text.getChars(0, text.length(), this.text, index);
        len += text.length();
        return this;
    }

    public TextEditor delete(int start, int end) {
        if (start < 0 || start > end || end > len) throw new IndexOutOfBoundsException("Index out of bounds");
        saveMemento();
        System.arraycopy(text, end, text, start, len - end);
        len -= (end - start);
        return this;
    }

    public TextEditor revers() {
        saveMemento();
        for (int i = 0; i < len/2; i++) {
            int lastIndex = len - 1 - i;
            char temp = text[i];
            text[i] = text[lastIndex];
            text[lastIndex] = temp;
        }
        return this;
    }


    public boolean undo() {
        TextMemento memento = repository.pop();
        if (memento == null) return false;
        text = Arrays.copyOf(memento.text, memento.capacity);
        len = memento.len;
        capacity = memento.capacity;
        return true;
    }


    private void increaseCapacity(int length) {
        int minCapacity = len + length;
        if (capacity < minCapacity) {
            int newCapacity = Math.max(capacity*2+2, minCapacity);
            text = Arrays.copyOf(text, newCapacity);
        }
    }


    private void convertNull() {
        int len = this.len + 4;
        increaseCapacity(4);
        text[len - 1] = 'l';
        text[len - 2] = 'l';
        text[len - 3] = 'u';
        text[len - 4] = 'n';
        this.len = len;
    }

    private void saveMemento() {
        repository.push(new TextMemento(text, len, capacity));
    }


    static class TextMemento {

        private final char[] text;
        private final int len;
        private final int capacity;

        public TextMemento(char[] text, int len, int capacity) {
            this.text = Arrays.copyOf(text,capacity);
            this.len = len;
            this.capacity = capacity;
        }
    }

    @Override
    public String toString() {
        return new String(text, 0, len);
    }
}
