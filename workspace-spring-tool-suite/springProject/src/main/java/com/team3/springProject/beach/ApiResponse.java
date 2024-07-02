package com.team3.springProject.beach;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
	
	 private int totCnt;
	 private boolean hasMore;
	 private List<Beach> data;
}
