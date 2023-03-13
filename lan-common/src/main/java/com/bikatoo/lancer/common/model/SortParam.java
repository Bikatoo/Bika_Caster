package com.bikatoo.lancer.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortParam {

    private String sort = "create_time";

    private SortOrder sortOrder = SortOrder.DESC;

    public String lastSql() {
        if (StringUtils.isBlank(sort)) {
            return "";
        }

        if (sortOrder == null) {
            sortOrder = SortOrder.ASC;
        }
        return "order by " + sort + " " + sortOrder.name();
    }

    public enum SortOrder {
        ASC,
        DESC,
    }
}
