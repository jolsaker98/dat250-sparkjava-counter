package no.hvl.dat250.rest.todos;

import static spark.Spark.*;

import com.google.gson.Gson;
/**
 * Rest-Endpoint.
 */
import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;


/**
 * Hello world!
 */
import com.google.gson.Gson;
import static spark.Spark.*;

public class TodoAPI {

    static TodoDao todos = new TodoDao();
    public static void main(String[] args) {

        if (args.length > 0)
            port(Integer.parseInt(args[0]));
        else
            port(8080);

        after((req, res) -> {
            res.type("application/json");
        });

        get("/todos", (req, res) -> new Gson().toJsonTree(todos.getTodos()));
        get("/todos/:id", (req, res) -> {
            try {
                Long.parseLong((req.params(":id")));
                Todo todo = todos.getTodo(req.params(":id"));
                if (todo == null)
                    return String.format("Todo with the id \"%s\" not found!", req.params(":id"));
                else
                    return new Gson().toJson(todo);
            }
            catch(NumberFormatException e) {
                return String.format("The id \"%s\" is not a number!", req.params(":id"));
            }
        });
        ;
        delete("/todos/:id", (req, res) -> {
            try {
                Long.parseLong((req.params(":id")));
                Todo todo = todos.getTodo(req.params(":id"));
                if (todo == null)
                    return String.format("Todo with the id \"%s\" not found!", req.params(":id"));
                else
                    todos.deleteTodo(todo.getId());
                    return new Gson().toJson(todo);
            }
            catch(NumberFormatException e) {
                return String.format("The id \"%s\" is not a number!", req.params(":id"));
            }
        });
        post("/todos", (req, res) -> {
            Todo todo = new Gson().fromJson(req.body(), Todo.class);
            todos.addTodo(todo);
            return todo.toJson();
        });
        put("/todos/:id", (req, res) -> {
            try {
                Long.parseLong((req.params(":id")));
                Todo oldTodo = todos.getTodo(req.params(":id"));
                Todo newTodo = new Gson().fromJson(req.body(), Todo.class);
                if (oldTodo == null)
                    return String.format("Todo with the id \"%s\" not found!", req.params(":id"));
                else
                    todos.deleteTodo(oldTodo.getId());
                    todos.addTodo(newTodo);
                    return new Gson().toJson(newTodo);
            }
            catch(NumberFormatException e) {
                return String.format("The id \"%s\" is not a number!", req.params(":id"));
            }
        });
    }

}
