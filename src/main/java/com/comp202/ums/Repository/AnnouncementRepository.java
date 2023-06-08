package com.comp202.ums.Repository;
import com.comp202.ums.Entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {
        List<Announcement> getAnnouncementsByCourse_Id(Long id);
}
