package com.ronglian.kangrui.saas.research.admin.biz;

import com.ronglian.kangrui.saas.research.admin.entity.HospCenter;
import com.ronglian.kangrui.saas.research.admin.mapper.HospCenterMapper;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-27 13:54
 **/
@Service
@Slf4j
public class HospCenterBiz extends BaseBiz<HospCenterMapper,HospCenter> {

    @Autowired
    private HospCenterMapper hospCenterMapper ;


    public List<HospCenterVo> getHospCenterList() {
        return hospCenterMapper.getHospCenterList() ;
    }

}
