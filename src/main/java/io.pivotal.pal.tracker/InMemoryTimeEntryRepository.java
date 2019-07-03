package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    private HashMap<Long, TimeEntry> inMemoryDB = new HashMap<Long, TimeEntry>();
    private long autoGenId = 1L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry newEntry = new TimeEntry(
               timeEntry.getProjectId(),
               timeEntry.getUserId(),
               timeEntry.getDate(),
               timeEntry.getHours()
        );
        long id = autoGenId;
        newEntry.setId(id);
        autoGenId++;
        inMemoryDB.put(id,newEntry);
        return newEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return inMemoryDB.get(id);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if(inMemoryDB.get(id)==null)
            return null;
        TimeEntry updatedEntry = new TimeEntry(
                id,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );
        inMemoryDB.replace(id,updatedEntry);
        return updatedEntry;
    }

    @Override
    public void delete(long id) {
        inMemoryDB.remove(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(inMemoryDB.values());
    }
}
