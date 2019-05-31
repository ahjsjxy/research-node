package com.ronglian.kangrui.saas.research.admin.rpc;

import com.ronglian.kangrui.saas.research.admin.manager.HospCenterManager;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-27 14:12
 **/
@RestController
@RequestMapping("api")
@Slf4j
public class HospCenterService {

    @Autowired
    private HospCenterManager hospCenterManager ;

    @RequestMapping(value="/hospCenter/getHospCenterList",method = RequestMethod.GET)
    public List<HospCenterVo> getHospCenterList(){
        return hospCenterManager.getHospCenterList() ;
    }

}
