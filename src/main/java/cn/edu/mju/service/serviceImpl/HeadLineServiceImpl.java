package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.cache.JedisUtil;
import cn.edu.mju.dao.HeadLineDao;
import cn.edu.mju.entity.HeadLine;
import cn.edu.mju.exceptions.HeadLineOperationException;
import cn.edu.mju.service.HeadLineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class HeadLineServiceImpl extends BaseServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    @Autowired
    private JedisUtil.Keys jedisKeys;





    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLine) {

        List<HeadLine> headLineList = null;
        ObjectMapper mapper = new ObjectMapper();
        String key = HEAD_LINE_LIST_KEY;
        if (headLine.getEnableStatus() != null) {
            key = key + "_" + headLine.getEnableStatus();
        }
        if (!jedisKeys.exists(key)) {
            headLineList = headLineDao.queryHeadLine(headLine);
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(headLineList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new HeadLineOperationException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory()
                    .constructParametricType(ArrayList.class, HeadLine.class);
            try {
                headLineList = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                throw new HeadLineOperationException(e.getMessage());
            }
        }
        return headLineList;
    }

    @Override
    public HeadLine getHeadLineById(Long lineId) {
        return headLineDao.queryHeadLineById(lineId);
    }
}
