package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.cache.JedisUtil;
import cn.edu.mju.dao.AreaDao;
import cn.edu.mju.entity.Area;
import cn.edu.mju.entity.Shop;
import cn.edu.mju.exceptions.AreaOperationException;
import cn.edu.mju.service.AreaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AreaServiceImpl extends BaseServiceImpl implements AreaService{

    @Autowired
    private AreaDao areaDao;


    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Autowired
    private JedisUtil.Strings jedisStrings;



    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Override
    public List<Area> getAreaList() {

        //从jedis中取数据
        String key = AREA_LIST_KEY;
        List<Area> areaList = null;
        ObjectMapper mapper = new ObjectMapper();
        if(!jedisKeys.exists(key)){
            areaList = areaDao.queryAreas();
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
            jedisStrings.set(key,jsonString);
        } else{
            //jedis中存在
            String jsonString = jedisStrings.get(key);
            //转化为java类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,Area.class);
            try {
                areaList = mapper.readValue(jsonString,javaType);
            } catch (IOException e) {
                e.printStackTrace();
                throw new AreaOperationException(e.getMessage());
            }

        }

        return areaList;
    }


}
