package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.StatisDTO;
import com.linkin.simi.model.StatisSearchDTO;

public interface StatisService {

	public List<StatisDTO> getStatis(StatisSearchDTO statisSearchDTO);

}
