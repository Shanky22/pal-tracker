package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    private TimeEntryRepository timeEntriesRepo;

    public TimeEntryController(
            TimeEntryRepository timeEntriesRepo,
            MeterRegistry meterRegistry
    ) {
        this.timeEntriesRepo = timeEntriesRepo;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        TimeEntry createdTimeEntry = timeEntriesRepo.create(timeEntry);
        actionCounter.increment();
        timeEntrySummary.record(timeEntriesRepo.list().size());
        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId){
        TimeEntry timeEntryContent = timeEntriesRepo.find(timeEntryId);
        if (timeEntryContent != null){
            actionCounter.increment();
            return new ResponseEntity<TimeEntry>(timeEntryContent,HttpStatus.OK);
        }else{
            return new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        return new ResponseEntity<>(timeEntriesRepo.list(), HttpStatus.OK);
    }

    @PutMapping("{timeEntryId}")
    public  ResponseEntity update(@PathVariable long timeEntryId,@RequestBody TimeEntry timeEntry)
    {
        TimeEntry timeEntryContent = timeEntriesRepo.update(timeEntryId,timeEntry);
        if(timeEntryContent != null) {
            actionCounter.increment();
            return new ResponseEntity<>(timeEntryContent, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{timeEntryId}")
    public  ResponseEntity delete(@PathVariable long timeEntryId)
    {
        timeEntriesRepo.delete(timeEntryId);
        actionCounter.increment();
        timeEntrySummary.record(timeEntriesRepo.list().size());
        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
