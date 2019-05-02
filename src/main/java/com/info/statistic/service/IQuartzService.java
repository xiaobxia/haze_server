package com.info.statistic.service;

import java.util.Date;

/**
 * Created by tl on 2018/5/4.
 */
public interface IQuartzService {

    String perfectQuartz(Date sDate, Date eDate);

    String perfectQuartzByModel(Date sDate, Date eDate);

    String perfectQuartzByChannel(Date sDate, Date eDate);

    void doQuartz(Date pdate);

    void doQuartzByModel(Date pdate);

    void doQuartzByChannel(Date pdate);
}
