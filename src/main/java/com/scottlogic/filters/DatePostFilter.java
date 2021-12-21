package com.scottlogic.filters;

import com.scottlogic.PostFilter;
import com.scottlogic.UserPost;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatePostFilter implements PostFilter {
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;

    public DatePostFilter(OffsetDateTime startDateInput, OffsetDateTime endDateInput) {
        this.startDate = startDateInput;
        this.endDate = endDateInput;
    }

    @Override
    public List<UserPost> filter(List<UserPost> inputList) {
        if (inputList == null) {
            return null;
        }
        List<UserPost> outputList = new ArrayList<>(inputList);
        return outputList
                .stream()
                .filter(a ->
                        (a.getDateTime().compareTo(startDate) > 0)
                                && (a.getDateTime().compareTo(endDate) < 0))
                .collect(Collectors.toList());
    }
}
