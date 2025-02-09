package com.example.crudschedule.service;

import com.example.crudschedule.dto.ScheduleRequestDto;
import com.example.crudschedule.dto.ScheduleResponseDto;
import com.example.crudschedule.entity.Schedule;
import com.example.crudschedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto save(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getTitle(), dto.getContent());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContent());
    }

    public List<ScheduleResponseDto> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleResponseDto> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            dtos.add(new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContent()));
        }
        return dtos;
    }

    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id에 해당하는 일정이 없습니다.")
        );
        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContent());
    }

    public ScheduleResponseDto update(Long id, ScheduleRequestDto dto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id에 해당하는 일정이 없습니다.")
        );
        schedule.update(dto.getTitle(), dto.getContent());
        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContent());
    }

    public void delete(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 id에 해당하는 일정이 없습니다.");
        }
        scheduleRepository.deleteById(id);
    }
}
