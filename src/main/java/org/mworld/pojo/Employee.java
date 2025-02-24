package org.mworld.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.mworld.utils.CustomTimestampSerializer;

import java.sql.Timestamp;

public class Employee {

    public Employee(String id, String name, Timestamp dateOfBirth) {
        this.employeeId = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    @JsonProperty("employee_id")
    private String employeeId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Calcutta")
    @JsonSerialize(using = CustomTimestampSerializer.class)
    private Timestamp dateOfBirth;

}
