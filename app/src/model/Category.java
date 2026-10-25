package model;

public class Category {
    private final int id;
    private final String name;
    private final boolean active;
    private final Integer parentId; // Integer, not int, because parent_id can be NULL

    public Category(int id, String name, boolean active, Integer parentId) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.parentId = parentId;
    }

    // short form used by the earlier CategoryDao
    public Category(int id, String name) {
        this(id, name, true, null);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public Integer getParentId() {
        return parentId;
    }

    @Override
    public String toString() {
        return name;
    } // shown in combo boxes
}