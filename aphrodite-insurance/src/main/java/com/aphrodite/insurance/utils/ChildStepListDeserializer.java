package com.aphrodite.insurance.utils;

import com.aphrodite.common.utils.JsonUtils;
import com.ethan.step.dto.CompositeStepInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.*;

/**
 * 聚合步骤的子步骤，需要动态配置。如下所示，步骤必须按照下标顺序执行。如果一个下标对应多个步骤，则该下标对应的步骤支持并行执行。
 * <P>1: 步骤1
 * <P>2: 步骤2 步骤3 步骤4
 * <P>3: 步骤5
 */
public class ChildStepListDeserializer extends JsonDeserializer<Map<Integer, List<CompositeStepInfo>>> {

    @Override
    public Map<Integer, List<CompositeStepInfo>> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        Map<Integer, List<CompositeStepInfo>> resMap = new HashMap<>();

        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            JsonNode value = entry.getValue();
            List<CompositeStepInfo> list = new ArrayList<>();

            if (value.isArray()) { // 是集合
                ArrayNode jsonList = (ArrayNode) value;
                for (JsonNode jsonNode : jsonList) {
                    list.add(JsonUtils.treeToValue(jsonNode, CompositeStepInfo.class));
                }
            } else { // 是单个步骤
                list.add(JsonUtils.treeToValue(value, CompositeStepInfo.class));
            }
            resMap.put(Integer.valueOf(entry.getKey()), list);
        }
        return resMap;
    }
}
