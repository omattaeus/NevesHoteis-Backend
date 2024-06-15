package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Hotel;
import br.com.nevesHoteis.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotelService {
    @Autowired
    private HotelRepository repository;

    public Page<Hotel> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Hotel save(Hotel hotel) {
        return repository.save(hotel);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public Hotel findById(Long id) {
        return repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Entity not found"));
    }
    @Transactional
    public Hotel update(Long id, Hotel hotelDto) {
        Hotel hotel = findById(id);
        return hotel.merge(hotelDto);
    }
}
