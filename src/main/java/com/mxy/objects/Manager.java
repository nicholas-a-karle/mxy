package com.mxy.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * This is meant to help Controller and Model manage the database
 * The classes are designed to update themselves so there is no need here
 * It softly manages  the memory used up by the memory instances of the database documents
 * 
 * As of right now, it removes the item which was added at the moment with the highest memory usage
 * Memory usage calculated by the classes in a janky sort of way
 * This means that via calls on the classes, you can make it go way over memroy usage
 * 
 * Reccomended maxSize is over 1000, really small numbers still if you do that
 * Set default to 10000
 * 
 * This manager has minor interfaces with the database, the individual classes have the majority of interaction.
 * The controller does insertion into the database
 * 
 */

import org.bson.types.ObjectId;

import com.mxy.admin.Database;

public class Manager {

    private Map<ObjectId, User> users;
    private Map<ObjectId, Post> posts;
    private Map<ObjectId, Login> logins;
    private Map<ObjectId, Registration> registrations;
    private Map<ObjectId, Group> groups;
    private Map<ObjectId, Repost> reposts;

    private int maxSize;
    private PriorityQueue<ObjectWrapper> priorityQueue;
    private Database database;

    public Manager(int maxSize, Database database) {
        this.maxSize = maxSize;
        users = new HashMap<>();
        posts = new HashMap<>();
        logins = new HashMap<>();
        registrations = new HashMap<>();
        groups = new HashMap<>();
        reposts = new HashMap<>();

        priorityQueue = new PriorityQueue<>(maxSize, (pw1, pw2) -> Long.compare(pw1.getPriority(), pw2.getPriority()));
        this.database = database;
    }

    public Manager(Database database) {
        maxSize = 10000;
        users = new HashMap<>();
        posts = new HashMap<>();
        logins = new HashMap<>();
        registrations = new HashMap<>();
        groups = new HashMap<>();
        reposts = new HashMap<>();

        priorityQueue = new PriorityQueue<>(maxSize, (pw1, pw2) -> Long.compare(pw1.getPriority(), pw2.getPriority()));
        this.database = database;
    }

    private void addUser(User user) {
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            addToPriorityQueue(user, priorityCalc());
        }
    }

    private void addUser(ObjectId userId) {
        addUser(new User(userId, database));
    }

    private void addPost(Post post) {
        if (!posts.containsKey(post.getId())) {
            posts.put(post.getId(), post);
            addToPriorityQueue(post, priorityCalc());
        }
    }

    private void addPost(ObjectId postId) {
        addPost(new Post(postId, database));
    }

    private void addLogin(Login login) {
        if (!logins.containsKey(login.getId())) {
            logins.put(login.getId(), login);
            addToPriorityQueue(login, priorityCalc());
        }
    }

    private void addLogin(ObjectId loginId) {
        addLogin(new Login(loginId, database));
    }

    private void addRegistration(Registration registration) {
        if (!registrations.containsKey(registration.getId())) {
            registrations.put(registration.getId(), registration);
            addToPriorityQueue(registration, priorityCalc());
        }
    }

    private void addRegistration(ObjectId registrationId) {
        addRegistration(new Registration(registrationId, database));
    }

    private void addGroup(Group group) {
        if (!groups.containsKey(group.getId())) {
            groups.put(group.getId(), group);
            addToPriorityQueue(group, priorityCalc());
        }
    }

    private void addGroup(ObjectId groupId) {
        addGroup(new Group(groupId, database));
    }

    private void addRepost(Repost repost) {
        if (!reposts.containsKey(repost.getId())) {
            reposts.put(repost.getId(), repost);
            addToPriorityQueue(repost, priorityCalc());
        }
    }

    private void addRepost(ObjectId repostId) {
        addRepost(new Repost(repostId, database));
    }

    public User getUser(ObjectId userId) {
        if (!users.containsKey(userId)) addUser(userId);
        return users.get(userId);
    }

    public Post getPost(ObjectId postId) {
        if (!posts.containsKey(postId)) addPost(postId);
        return posts.get(postId);
    }

    public Login getLogin(ObjectId loginId) {
        if (!logins.containsKey(loginId)) addLogin(loginId);
        return logins.get(loginId);
    }

    public Registration getRegistration(ObjectId registrationId) {
        if (!registrations.containsKey(registrationId)) addRegistration(registrationId);
        return registrations.get(registrationId);
    }

    public Group getGroup(ObjectId groupId) {
        if (!groups.containsKey(groupId)) addGroup(groupId);
        return groups.get(groupId);
    }

    public Repost getRepost(ObjectId repostId) {
        if (!reposts.containsKey(repostId)) addRepost(repostId);
        return reposts.get(repostId);
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        List<ObjectId> idList = database.getAllUsers();

        for (ObjectId id: idList) {
            userList.add(new User(id, database));
        }

        return userList;
    }

    public List<Group> getAllGroups() {
        List<Group> groupList = new ArrayList<>();
        List<ObjectId> idList = database.getAllGroups();

        for (ObjectId id: idList) {
            groupList.add(new Group(id, database));
        }

        return groupList;
    }

    private int priorityCalc() {
        return memorySize();
    }

    private int memorySize() {
        int count = 0;
        for (User user : users.values()) count += user.memSize();
        for (Post post : posts.values()) count += post.memSize();
        for (Repost repost : reposts.values()) count += repost.memSize();
        for (Group group : groups.values()) count += group.memSize();
        for (Registration reg : registrations.values()) count += reg.memSize();
        for (Login login : logins.values()) count += login.memSize();
        return count;
    }

    private void addToPriorityQueue(Object obj, int weight) {
        ObjectWrapper wrapper = new ObjectWrapper(obj, weight);
        priorityQueue.offer(wrapper);

        if (memorySize() > maxSize) {
            ObjectWrapper removed = priorityQueue.poll();
            removeObjectFromMap(removed.getObject());
        }
    }

    private void removeObjectFromMap(Object obj) {
        if (obj instanceof User) {
            for (Map.Entry<ObjectId, User> entry : users.entrySet()) {
                if (entry.getValue().equals(obj)) {
                    users.remove(entry.getKey());
                    break;
                }
            }
        } else if (obj instanceof Post) {
            for (Map.Entry<ObjectId, Post> entry : posts.entrySet()) {
                if (entry.getValue().equals(obj)) {
                    posts.remove(entry.getKey());
                    break;
                }
            }
        } else if (obj instanceof Login) {
            for (Map.Entry<ObjectId, Login> entry : logins.entrySet()) {
                if (entry.getValue().equals(obj)) {
                    logins.remove(entry.getKey());
                    break;
                }
            }
        } else if (obj instanceof Registration) {
            for (Map.Entry<ObjectId, Registration> entry : registrations.entrySet()) {
                if (entry.getValue().equals(obj)) {
                    registrations.remove(entry.getKey());
                    break;
                }
            }
        } else if (obj instanceof Group) {
            for (Map.Entry<ObjectId, Group> entry : groups.entrySet()) {
                if (entry.getValue().equals(obj)) {
                    groups.remove(entry.getKey());
                    break;
                }
            }
        } else if (obj instanceof Repost) {
            for (Map.Entry<ObjectId, Repost> entry : reposts.entrySet()) {
                if (entry.getValue().equals(obj)) {
                    reposts.remove(entry.getKey());
                    break;
                }
            }
        }
    }

    public void processNextItem() {
        if (!priorityQueue.isEmpty()) {
            ObjectWrapper wrapper = priorityQueue.poll();
            Object obj = wrapper.getObject();
            System.out.println("Processing: " + obj);
            removeObjectFromMap(obj);
        } else {
            System.out.println("Priority queue is empty.");
        }
    }

    private static class ObjectWrapper {
        private Object object;
        private long priority;

        public ObjectWrapper(Object object, int priority) {
            this.object = object;
            this.priority = priority;
        }

        public Object getObject() {
            return object;
        }

        public long getPriority() {
            return priority;
        }
    }
}
