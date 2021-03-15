package io.tazabreu.springrestapi.domain.enumerable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;

public enum QuestStatus {
    NOT_ASSIGNED, IN_PROGRESS, COMPLETED;

    public static boolean isTransitionValid(QuestStatus currentStatus, QuestStatus nextStatus) {
        EnumMap<QuestStatus, Collection<QuestStatus>> transitionMap = new EnumMap(QuestStatus.class);
        transitionMap.put(NOT_ASSIGNED, Arrays.asList(IN_PROGRESS));
        transitionMap.put(IN_PROGRESS, Arrays.asList(NOT_ASSIGNED, COMPLETED));
        transitionMap.put(COMPLETED, Collections.emptyList());
        return transitionMap.get(currentStatus).contains(nextStatus);
    }
}