package com.business.OperationsManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairStatsResponse {
	private long accepted;
    private long inProgress;
    private long repaired;
    private long returned;
}
