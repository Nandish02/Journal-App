package androidsamples.java.journalapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "journal_table")
public class JournalEntry {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private UUID entryId;

    @ColumnInfo(name = "title")
    private String entryTitle;

    @ColumnInfo(name = "date")
    private String entryDate;

    @ColumnInfo(name = "start_time")
    private String entryStartTime;

    @ColumnInfo(name = "end_time")
    private String entryEndTime;

    public JournalEntry(@NonNull String title, String date, String startTime, String endTime) {
        this.entryId = UUID.randomUUID();
        this.entryTitle = title;
        this.entryDate = date;
        this.entryStartTime = startTime;
        this.entryEndTime = endTime;
    }

    @NonNull
    public UUID getEntryId() {
        return entryId;
    }

    public String getEntryTitle() {
        return entryTitle;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getEntryStartTime() {
        return entryStartTime;
    }

    public String getEntryEndTime() {
        return entryEndTime;
    }

    public void setEntryId(@NonNull UUID entryId) {
        this.entryId = entryId;
    }

    public void setEntryTitle(String entryTitle) {
        this.entryTitle = entryTitle;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public void setEntryStartTime(String entryStartTime) {
        this.entryStartTime = entryStartTime;
    }

    public void setEntryEndTime(String entryEndTime) {
        this.entryEndTime = entryEndTime;
    }

    // Performing a more meaningful redundant operation
    public void logEntryDetails() {
        String logMessage = "Logging details for entry: " +
                "Title - " + entryTitle +
                ", Date - " + entryDate +
                ", Start Time - " + entryStartTime +
                ", End Time - " + entryEndTime;
        System.out.println(logMessage);
    }
}
