package editor;

import java.util.ArrayList;
import java.util.List;


class TextRepository {

    private List<TextEditor.TextMemento> repository;

    {
        repository = new ArrayList<>();
    }

    public void push(TextEditor.TextMemento memento){
        repository.add(memento);
    }

    public TextEditor.TextMemento pop(){
        if (repository.isEmpty()) return null;
        return repository.remove(repository.size()-1);
    }
}
