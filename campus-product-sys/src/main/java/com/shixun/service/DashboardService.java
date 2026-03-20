package com.shixun.service;

import com.shixun.pojo.api.Result;
import com.shixun.pojo.vo.ChartVO;

import java.util.List;

/**
 * 仪表盘业务逻辑接口
 */
public interface DashboardService {

    Result<List<ChartVO>> staticCount();

    Result<List<ChartVO>> productShelvesInfo(Integer day);

}
