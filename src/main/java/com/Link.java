package com;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private Map<Integer, Integer> nodes = new HashMap<>();
    private String data;

    public Link(Integer departNode, Integer destinNode, String data) {
        this.nodes.put(departNode, destinNode);
        this.data = data;
    }
}
