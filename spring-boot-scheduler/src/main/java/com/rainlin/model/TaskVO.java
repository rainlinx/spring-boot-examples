package com.rainlin.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 定时任务VO
 *
 * @author rainlin
 */
@Data
@AllArgsConstructor
public class TaskVO {
    private String taskName;
    private String expression;
}
