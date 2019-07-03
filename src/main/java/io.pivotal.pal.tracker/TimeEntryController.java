package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    //private TimeEntryRepository timeEntriesRepo;

 //   public TimeEntryController() {
   // }

    private TimeEntryRepository timeEntriesRepo;

    public TimeEntryController(TimeEntryRepository timeEntriesRepo) {
        this.timeEntriesRepo = timeEntriesRepo;
    }


    // @PostMapping
  //  public ResponseEntity create(@RequestBody TimeEntry timeEntry){
    //    TimeEntry timeEntryContent = timeEntryRepository.create(timeEntry);
      //  return new ResponseEntity(timeEntryContent, HttpStatus.CREATED);
    //}

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        TimeEntry createdTimeEntry = timeEntriesRepo.create(timeEntry);

        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId){
        TimeEntry timeEntryContent = timeEntriesRepo.find(timeEntryId);
        if (timeEntryContent != null){
            return new ResponseEntity<TimeEntry>(timeEntryContent,HttpStatus.OK);
        }else{
            return new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }
    }

   // @GetMapping
    //public ResponseEntity<List<TimeEntry>> list()
    //{
     //   List<TimeEntry> timeEntryContent =timeEntryRepository.list();
       // if(!timeEntryContent.isEmpty()) {
         //   return new ResponseEntity(timeEntryContent, HttpStatus.OK);
        //}else{
          //  return new ResponseEntity(HttpStatus.NO_CONTENT);
        //}
    //}

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(timeEntriesRepo.list(), HttpStatus.OK);
    }

    @PutMapping("{timeEntryId}")
    public  ResponseEntity update(@PathVariable long timeEntryId,@RequestBody TimeEntry timeEntry)
    {
        TimeEntry timeEntryContent = timeEntriesRepo.update(timeEntryId,timeEntry);
        if(timeEntryContent != null) {
            return new ResponseEntity<>(timeEntryContent, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{timeEntryId}")
    public  ResponseEntity delete(@PathVariable long timeEntryId)
    {
        timeEntriesRepo.delete(timeEntryId);
        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
