package com;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Data
@NoArgsConstructor
public class DecisionMakingTree {
    private Node currentNode;
    private List<Node> nodes;
    private List<Link> links;
    private Stack<Integer> history;

    public DecisionMakingTree(Node rootNode, List<Node> nodes, List<Link> links) {
        this.currentNode = rootNode;
        this.nodes = nodes;
        this.links = links;
        this.history = new Stack<>();
        this.history.push(rootNode.getIndex());
    }

    public Node getNodeByIndex(Integer index) {
        int left = 0;
        int right = nodes.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            Node midNode = nodes.get(mid);
            Integer midIndex = midNode.getIndex();

            if (midIndex.equals(index)) {
                return midNode;
            } else if (midIndex < index) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    public void moveToPreviousNode() {
        if (history.size() > 1) {
            history.pop();
            currentNode = getNodeByIndex(history.peek());
        }
    }

    public void moveToNextNode(Link link) {
        Map<Integer, Integer> nodesMap = link.getNodes();
        Integer nextNodeIndex = nodesMap.get(currentNode.getIndex());
        Node nextNode = getNodeByIndex(nextNodeIndex);
        if (nextNode != null) {
            currentNode = nextNode;
            history.push(nextNodeIndex);
        }
    }

    public List<Link> getPossibleLinks() {
        if (currentNode.getIsEndOfSearch()) {
            return null;
        }
        List<Link> possibleLinks = new ArrayList<>();
        for (Link link : links) {
            if (link.getNodes().containsKey(currentNode.getIndex())) {
                possibleLinks.add(link);
            }
        }
        return possibleLinks;
    }
}
