package Infrastructure.Tasks.Sessions;

import Infrastructure.Tasks.Tasks.Task;

import java.util.ArrayList;

/**
 * Created by Markan on 25.02.2017.
 */
public abstract class TaskSession<T> implements ITaskSession<T> {
    ArrayList<Task> tasks = new ArrayList<>();
    @Override
    public int getResult() {
        int res = 0;
        for (Task task : tasks)
            if (task.isCorrect)
                res++;
        return res;
    }


    public Task getTask(int index) {
        return tasks.get(index);
    }

    @Override
    public ArrayList getGenerategData() {
        return tasks;
    }
}
