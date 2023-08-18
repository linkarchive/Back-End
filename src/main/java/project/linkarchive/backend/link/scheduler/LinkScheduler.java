package project.linkarchive.backend.link.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.repository.LinkRepository;

import java.time.LocalDateTime;
import java.util.List;

import static project.linkarchive.backend.advice.data.DataConstants.DEFAULT_COUNT;
import static project.linkarchive.backend.advice.data.DataConstants.TWO_WEEK;

@Component
@Transactional
public class LinkScheduler {

    private static final Logger logger = LoggerFactory.getLogger(LinkScheduler.class);

    private final LinkRepository linkRepository;

    public LinkScheduler(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldLinksFromTrash() {
        logger.info("Schedule: 매 자정마다 일주일 지난 삭제된 링크 일괄 삭제");

        LocalDateTime twoWeekAgo = LocalDateTime.now().minusWeeks(TWO_WEEK);
        List<Link> trashLinks = linkRepository.findOldLinksInTrashStatus(twoWeekAgo);

        int deletedCount = trashLinks.size();

        if (deletedCount == DEFAULT_COUNT) {
            logger.info("삭제될 링크가 존재하지 않습니다.");
            return;
        }

        trashLinks.forEach(
                link -> {
                    linkRepository.deleteById(link.getId());
                }
        );

        logger.info("Delete: {} 개의 삭제보관함의 링크 삭제 완료.", deletedCount);
    }

}
