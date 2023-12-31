package ru.practicum.event.service;

import ru.practicum.event.dto.EventFullCommentDto;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventSearchParams;
import ru.practicum.event.dto.UpdateEventAdminRequest;

import java.util.List;

public interface EventAdminService {
    List<EventFullDto> getEvents(EventSearchParams params);

    EventFullCommentDto updateEvent(long eventId, UpdateEventAdminRequest updateEventAdminRequest);
}
