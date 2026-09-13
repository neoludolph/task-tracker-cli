package de.neoludolph.task_tracker_cli.App;

import de.neoludolph.task_tracker_cli.Repository.TaskRepositoryImpl;
import de.neoludolph.task_tracker_cli.Service.TaskServiceImpl;

import java.util.Set;

public class TrackerApp {

    public static void startApp(String[] args) {
        TaskServiceImpl taskService = new TaskServiceImpl(new TaskRepositoryImpl());

        if (!(args[0].equals("add"))
                && !(args[0].equals("update"))
                && !(args[0].equals("delete"))
                && !(args[0].equals("mark-in-progress"))
                && !(args[0].equals("mark-done"))
                && !(args[0].equals("list"))
                && !(args[0].equals("list done"))
                && !(args[0].equals("list todo"))
                && !(args[0].equals("list in-progress")))
        {
            throw new IllegalArgumentException("Please enter a valid operation!"
            + " Valid operations:\n"
            + "\tadd\n"
            + "\tupdate\n"
            + "\tdelete\n"
            + "\tmark-in-progress\n"
            + "\tmark-done\n"
            + "\tlist\n"
            + "\tlist done\n"
            + "\tlist todo\n"
            + "\tlist in-progress");
        }

        switch (args[0]) {
            case "add":
                if (!(args[1] instanceof String)) {
                    throw new IllegalArgumentException("Please enter your task within quotation marks, for example: "
                    + "\"Buy groceries and cook dinner\"");
                }
                taskService.add(args[1]);
                break;
            case "update":
                if (!(args[2] instanceof String)) {
                    throw new IllegalArgumentException("Please enter your task within quotation marks, for example: "
                    + "\"Buy groceries and cook dinner\"");
                }
                try {
                    Long.parseLong(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("\"" + args[1] + "\"" + " is not an id! Please enter a valid id as a number.");
                }
                taskService.update(Long.parseLong(args[1]), args[2]);
                break;
            case "delete":
                try {
                    Long.parseLong(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("\"" + args[1] + "\"" + " is not an id! Please enter a valid id as a number.");
                }
                taskService.delete(Long.parseLong(args[1]));
                break;
            case "mark-in-progress":
                try {
                    Long.parseLong(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("\"" + args[1] + "\"" + " is not an id! Please enter a valid id as a number.");
                }
                taskService.markInProgress(Long.parseLong(args[1]));
                break;
            case "mark-done":
                try {
                    Long.parseLong(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("\"" + args[1] + "\"" + " is not an id! Please enter a valid id as a number.");
                }
                taskService.markDone(Long.parseLong(args[1]));
                break;
            case "list":
                taskService.listAll();
        }
    }
}
