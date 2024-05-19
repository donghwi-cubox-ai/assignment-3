package com.test.board.repository;

import com.test.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.userId = :userId AND m.password = :password")
    Optional<Member> findByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    Optional<Member> findByUserId(String userId);
}
