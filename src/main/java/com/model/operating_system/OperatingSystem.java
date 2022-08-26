package com.model.operating_system;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperatingSystem {
    private String designation;
    private int version;

    public OperatingSystem(String designation, int version) {
        this.designation = designation;
        this.version = version;
    }
}
