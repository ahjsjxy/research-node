package com.ronglian.kangrui.saas.research.admin.api;

import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-27 14:44
 **/
@FeignClient(value = "kr-admin")
public interface IHospCenterService {
    @RequestMapping(value="/api/hospCenter/getHospCenterList",method = RequestMethod.GET)
    public List<HospCenterVo> getHospCenterList() ;
}
