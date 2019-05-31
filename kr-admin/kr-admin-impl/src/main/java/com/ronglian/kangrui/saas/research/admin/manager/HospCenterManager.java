package com.ronglian.kangrui.saas.research.admin.manager;

import com.ronglian.kangrui.saas.research.admin.biz.HospCenterBiz;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-27 14:03
 **/
@Service
public class HospCenterManager {
    @Autowired
    private HospCenterBiz hospCenterBiz ;

    /**
     * 平台有效的医疗机构列表
     * @return
     */
    public List<HospCenterVo> getHospCenterList() {
        return hospCenterBiz.getHospCenterList() ;
    }
}
