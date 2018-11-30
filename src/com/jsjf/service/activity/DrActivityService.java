package com.jsjf.service.activity;

import java.util.Map;

public interface DrActivityService {
	 /**
     * 领取周年庆
     * @param map
     */
    public void receiveAnniversaryAmount(Map<String,Object> map);
    /**
     * 获取双11往期排行榜
     * @param map
     */
    public void getDouble11LastKing();
    /**
     * 添加双12返现
     */
    public void insertDouble12CashBack();
}
