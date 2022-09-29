package no.hvl.dat250.rest.todos;


import java.util.ArrayList;

public class TodoDao {

    private ArrayList<Todo> list = new ArrayList<>();

    public TodoDao() {
    }

    public void addTodo (Todo todo) {
        list.add(todo);
    }

    public ArrayList<Todo> getTodos() {
        return list;
    }

    public Todo getTodo (String id) {
        for (Todo t : list) {
            if (t.getId().equals(Long.parseLong(id)))
                return t;
        }
        return null;
    }

    public void deleteTodo (Long id) {
        list.removeIf(t -> t.getId().equals(id));
    }

}
