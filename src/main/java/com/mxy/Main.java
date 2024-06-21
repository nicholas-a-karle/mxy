package com.mxy;

import com.mxy.admin.Controller;
import com.mxy.admin.Database;
import com.mxy.objects.Manager;

public class Main {
    public static void main(String[] args) {
        
        Database database = new Database();
        Manager manager = new Manager(database);
        Controller controller = new Controller(database, manager);

        System.out.println(controller.getMetricNumUsers());

    }
}