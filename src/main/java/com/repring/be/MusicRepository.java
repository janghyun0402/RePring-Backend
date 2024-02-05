package com.repring.be;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {

}
