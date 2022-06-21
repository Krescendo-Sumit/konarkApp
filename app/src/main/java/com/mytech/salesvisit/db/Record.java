package com.mytech.salesvisit.db;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters({GeoType.class,RecordType.class})
public class Record {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "createdAt")
    private long createdAt;
    @ColumnInfo(name = "customerId")
    private int customerId;
    @Embedded
    private GeoLocation location;
    @ColumnInfo(name = "recordType")
    private RecordType recordType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", customerId=" + customerId +
                ", location=" + location +
                ", recordType=" + recordType +
                '}';
    }
}
