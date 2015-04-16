package task;

import config.Messages;
import config.Parameter;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskScheduler {

    private ArrayList<Task> queue;
    private HashMap<Integer, ArrayList<String>> dependencies;
    private int count;

    /**
     * Constructor
     */
    public TaskScheduler()
    {
        this.count = 0;
        this.queue = new ArrayList<Task>();
        this.dependencies = new HashMap<Integer, ArrayList<String>>();
    }

    /**
     * Get where task is stored in queue
     * @param task
     * @return
     */
    private  int getIndex(Task task)
    {
        for(int i=0;i<queue.size();i++)
        {
            Task t = queue.get(i);
            if(t.tag.equals(task.tag) && t.sort == task.sort && t.cloud == task.cloud)
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * If task exist in queue
     * @param task
     * @return
     */
    private boolean ifExistTask(Task task)
    {
        int index = getIndex(task);
        return (index != -1);
    }

    /**
     * Adding a task
     * If Task Already Exist : Increment its Completed_State
     * @param task
     */
    public int add(Task task)
    {
        if(!task.status.equals(Messages.task_completed))
        {
            if(task.analyseParameters())
            {
                if(!ifExistTask(task))
                {
                    task.task_id = this.count;
                    queue.add(task);
                    dependencies.put(this.count,new ArrayList<String>());
                    this.count++;
                    return 0;
                } else
                {
                    int index = getIndex(task);
                    if(task.completed_state > 0)
                    { //Task Completed
                        task.status = Messages.task_completed;
                        queue.remove(task);
                        dependencies.remove(index);
                        return task.completed_state;
                    } else
                    { //Task Needs Sorting
                        task.completed_state++;
                        queue.set(index, task);
                        return task.completed_state;
                    }
                }
            } else
            {
                System.err.println("Error: Cannot Add to Scheduler : Problem with Task");
                return 2;
            }
        }

        return 2;
    }

    /**
     * Removing a task
     * @param task
     */
    public void remove(Task task)
    {
        for(int i=0;i<queue.size();i++)
        {
            Task t = queue.get(i);
            if(t.task_id == task.task_id)
            {
                if(t.status.equals(Messages.task_completed))
                {
                    queue.remove(task);
                    dependencies.remove(i);
                    break;
                } else
                {
                    System.err.println("Error: Cannot Remove from Scheduler : Task Not Completed");
                }
            }
        }
    }

    /**
     * Updating task status
     * @param task Task
     * @param status String
     */
    public void updateTaskStatus(Task task, String status)
    {
        for(int i=0;i<queue.size();i++) {
            Task t = queue.get(i);
            if (t.task_id == task.task_id) {
                t.status = status;
            }
        }
    }

    /**
     * Updating task status
     * @param task_id Integer
     * @param rawStatus String
     */
    public void updateTaskStatus(int task_id, String rawStatus)
    {
        for(int i=0;i<queue.size();i++) {
            Task t = queue.get(i);
            if (t.task_id == task_id) {
                if(rawStatus.equals(Messages.response_OK))
                {
                    //TODO remove completed task
                    t.status = Messages.task_completed;
                }
            }
        }
    }

    @Override
    public String toString()
    {
        return "TaskScheduler{" +
                "queue=" + queue +
                ", count=" + count +
                '}';
    }

    /**
     * Test
     * Main Method
     * @param args
     */
    public static void main(String[] args)
    {
        TaskScheduler scheduler = new TaskScheduler();
        Task task1 = new Task("barcelona",0,0);
        Task task2 = new Task("sunny",0,0);
        Task task3 = new Task("sunny",0,0);
        Task task4 = new Task("barcelona",0,0);

        scheduler.add(task1);
        scheduler.add(task2);
        scheduler.add(task3);
        System.out.println(scheduler);
        scheduler.updateTaskStatus(task2, Messages.task_completed);
        scheduler.remove(task2);
        scheduler.add(task4);
        System.out.println(scheduler);
    }

}
