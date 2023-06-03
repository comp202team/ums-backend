package com.comp202.ums.Repository;
import com.comp202.ums.Entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {

}
