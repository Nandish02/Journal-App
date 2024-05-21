package androidsamples.java.journalapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.UUID;

/**
 * ViewModel for managing details of a journal entry.
 */
public class EntryDetailsViewModel extends ViewModel {
    private final JournalRepository mRepository;

    // LiveData for observing changes in entry details
    private final MutableLiveData<UUID> entryIdLiveData = new MutableLiveData<>();

    // Temporary variables to store entry details
    private String tempTitle;
    private String tempDate;
    private String tempStartTime;
    private String tempEndTime;

    public EntryDetailsViewModel() {
        // Initializing the repository
        mRepository = JournalRepository.getInstance();
    }

    // Update an existing entry in the repository
    public void updateEntry(JournalEntry entry) {
        mRepository.update(entry);
    }

    // Delete an existing entry from the repository
    public void deleteEntry(JournalEntry entry) {
        mRepository.delete(entry);
    }

    // Insert a new entry into the repository
    public void insertEntry(JournalEntry entry) {
        mRepository.insert(entry);
    }

    // LiveData transformation to observe changes in the entry details
    LiveData<JournalEntry> getEntryLiveData() {
        return Transformations.switchMap(entryIdLiveData, mRepository::getEntry);
    }

    // Load entry details by providing entry ID
    public void loadEntry(UUID entryId) {
        entryIdLiveData.setValue(entryId);
    }

    // Clear temporary variables
    public void clearTemp() {
        this.tempTitle = null;
        this.tempDate = null;
        this.tempStartTime = null;
        this.tempEndTime = null;
    }

    // Set temporary title
    public void setTempTitle(String title) {
        this.tempTitle = title;
    }

    // Set temporary date
    public void setTempDate(String date) {
        this.tempDate = date;
    }

    // Set temporary start time
    public void setTempStartTime(String startTime) {
        this.tempStartTime = startTime;
    }

    // Set temporary end time
    public void setTempEndTime(String endTime) {
        this.tempEndTime = endTime;
    }

    // Get temporary title
    public String getTempTitle() {
        return tempTitle;
    }

    // Get temporary date
    public String getTempDate() {
        return tempDate;
    }

    // Get temporary start time
    public String getTempStartTime() {
        return tempStartTime;
    }

    // Get temporary end time
    public String getTempEndTime() {
        return tempEndTime;
    }
}
