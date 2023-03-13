package com.bikatoo.lancer.common.objectvalue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class AttrUpdate<T> {

    private T before;

    private T after;

    @JsonIgnore
    public boolean isUpdated() {
        return !Objects.equals(before, after);
    }

}
